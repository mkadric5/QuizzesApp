package ba.etf.rma21.projekat

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


class SpaceItemDecoration(private val spaceHeight: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        outRect.bottom = spaceHeight
        outRect.right = spaceHeight
    }
}

class KvizAdapter(
        private var dataSet: List<Kviz>
) : RecyclerView.Adapter<KvizAdapter.ViewHolder>() {
    /**
     *Klasa za pružanje referenci na sve elemente view-a
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nazivPredmeta: TextView
        val slikaKviza: ImageView
        val nazivKviza: TextView
        val datumKviza: TextView
        val trajanjeKviza: TextView
        val bodoviNaKvizu: TextView
        init {
            // Definisanje akcija na elemente
            nazivPredmeta = view.findViewById(R.id.nazivPredmeta)
            slikaKviza = view.findViewById(R.id.slikaKviza)
            nazivKviza = view.findViewById(R.id.nazivKviza)
            datumKviza = view.findViewById(R.id.datumKviza)
            trajanjeKviza = view.findViewById(R.id.trajanjeKviza)
            bodoviNaKvizu = view.findViewById(R.id.bodoviNaKvizu)
        }
    }
    // Kreiraj novi view
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Kreiraj novi view koji definiše UI elementa liste
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_kviz, viewGroup, false)
        return ViewHolder(view)
    }
    // Izmijeni sadržaj view-a
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //Pokupi element iz skupa podataka i zamijeni
        //sadržaj View sa odgovarajućim

        //bind naziva predmeta, naziva kviza, trajanja kviza
        viewHolder.nazivPredmeta.text = dataSet[position].nazivPredmeta
        viewHolder.nazivKviza.text = dataSet[position].naziv
        viewHolder.trajanjeKviza.text = dataSet[position].trajanje.toString().plus(" min")

        //bind bodova sa view-om
        if (dataSet[position].osvojeniBodovi != null)
            viewHolder.bodoviNaKvizu.text = dataSet[position].osvojeniBodovi.toString()
        else viewHolder.bodoviNaKvizu.text = ""

        //bind slike kolora sa view- om i bind datuma sa textview-om
        val datumPocetka = dataSet[position].datumPocetka
        val datumKraja = dataSet[position].datumKraj
        val datumRada = dataSet[position].datumRada
        val osvojeniBodovi = dataSet[position].osvojeniBodovi
        val bojaKviza: String

        val context = viewHolder.slikaKviza.context

        val dateFormat = SimpleDateFormat("dd.MM.yyyy")

        if (osvojeniBodovi != null && datumRada != null) {
            bojaKviza = "plava"
            viewHolder.datumKviza.text = dateFormat.format(datumRada)
        }
        else if (datumKraja > Calendar.getInstance().time && Calendar.getInstance().time > datumPocetka) {
            bojaKviza = "zelena"
            viewHolder.datumKviza.text = dateFormat.format(datumKraja)
        }

        else if (datumPocetka > Calendar.getInstance().time) {
            bojaKviza = "zuta"
            viewHolder.datumKviza.text = dateFormat.format(datumPocetka)
        }
        else {
            bojaKviza = "crvena"
            viewHolder.datumKviza.text = dateFormat.format(datumKraja)
        }

        val id: Int = context.resources.getIdentifier(bojaKviza,"drawable", context.packageName)
        viewHolder.slikaKviza.setImageResource(id)
    }
    // Vrati veličinu skupa
    override fun getItemCount() = dataSet.size

    fun updateDataSet(noviKvizovi: List<Kviz>) {
        dataSet = noviKvizovi
        //notifyDataSetChanged()
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var filterKvizova: Spinner
    private lateinit var listaKvizova: RecyclerView
    private lateinit var upisButton: FloatingActionButton
    private lateinit var kvizAdapter: KvizAdapter
    private lateinit var filterKvizovaAdapter: ArrayAdapter<String>
    private var kvizListViewModel = KvizListViewModel()
    private val LAUNCH_SECOND_ACTIVITY: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filterKvizova = findViewById(R.id.filterKvizova)
        listaKvizova = findViewById(R.id.listaKvizova)
        upisButton = findViewById(R.id.upisDugme)

        filterKvizovaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
        listOf("Svi moji kvizovi","Svi kvizovi","Urađeni kvizovi","Budući kvizovi","Prošli kvizovi(neurađeni)"))
        filterKvizovaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterKvizova.adapter = filterKvizovaAdapter

        kvizAdapter = KvizAdapter(kvizListViewModel.dajMojeKvizove().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)}))
        listaKvizova.layoutManager = GridLayoutManager(this,2)
        listaKvizova.addItemDecoration(SpaceItemDecoration(5))

        listaKvizova.adapter = kvizAdapter
        //kvizAdapter.notifyDataSetChanged()

        filterKvizova.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateLista(filterKvizova.selectedItem.toString())
            }
        }

        upisButton.setOnClickListener {
            showUpisPredmeta()
        }
    }

    private fun showUpisPredmeta() {
        val intent = Intent(this, UpisPredmet::class.java).apply {
        }
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val predmet: String? = data?.getStringExtra("predmet")
                val grupa: String? = data?.getStringExtra("grupa")

                if (predmet != null && grupa != null){
                        kvizListViewModel.upisiKorisnika(grupa,predmet)
                        kvizAdapter.updateDataSet(kvizListViewModel.dajMojeKvizove())
                        listaKvizova.adapter = kvizAdapter
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    private fun updateLista(filterNaziv: String) {
        when(filterNaziv) {
            "Svi moji kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajMojeKvizove().sortedWith(
                        Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)}
                ))
                listaKvizova.adapter = kvizAdapter
            }
            "Svi kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajSveKvizove().sortedWith(
                        Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)}
                ))
                listaKvizova.adapter = kvizAdapter
            }
            "Urađeni kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajUradjeneKvizove().sortedWith(
                        Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)}
                ))
                listaKvizova.adapter = kvizAdapter
            }
            "Budući kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajBuduceKvizove().sortedWith(
                        Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)}
                ))
                listaKvizova.adapter = kvizAdapter
            }
            "Prošli kvizovi(neurađeni)" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajNeuradjeneKvizove().sortedWith(
                        Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)}
                ))
                listaKvizova.adapter = kvizAdapter
            }
        }
    }
}
