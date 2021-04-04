package ba.etf.rma21.projekat

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
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

        //bind slike kolora sa view- om
        val datumPocetka = dataSet[position].datumPocetka
        val datumKraja = dataSet[position].datumKraj
        val datumRada = dataSet[position].datumRada
        val osvojeniBodovi = dataSet[position].osvojeniBodovi
        val bojaKviza: String

        val context = viewHolder.slikaKviza.context

        val c = Calendar.getInstance()

        if (osvojeniBodovi != null) {
            c.time = datumRada
            bojaKviza = "plava"
            viewHolder.datumKviza.text = c.get(Calendar.DAY_OF_MONTH).toString().plus(
                    "." + c.get(Calendar.MONTH).toString() + "." + c.get(Calendar.YEAR).toString())
        }
        else if (datumKraja > Calendar.getInstance().time && Calendar.getInstance().time > datumPocetka) {
            c.time = datumKraja
            bojaKviza = "zelena"
            viewHolder.datumKviza.text = c.get(Calendar.DAY_OF_MONTH).toString().plus(
                    "." + c.get(Calendar.MONTH).toString() + "." + c.get(Calendar.YEAR).toString())
        }

        else if (datumPocetka > Calendar.getInstance().time) {
            c.time = datumPocetka
            bojaKviza = "zuta"
            viewHolder.datumKviza.text = c.get(Calendar.DAY_OF_MONTH).toString().plus(
                    "." + c.get(Calendar.MONTH).toString() + "." + c.get(Calendar.YEAR).toString()
            )
        }
        else {
            c.time = datumKraja
            bojaKviza = "crvena"
            viewHolder.datumKviza.text = c.get(Calendar.DAY_OF_MONTH).toString().plus(
                    "." + c.get(Calendar.MONTH).toString() + "." + c.get(Calendar.YEAR).toString())
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
    private lateinit var kvizAdapter: KvizAdapter
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var kvizListViewModel = KvizListViewModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filterKvizova = findViewById(R.id.filterKvizova)
        listaKvizova = findViewById(R.id.listaKvizova)

        arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
        listOf("Svi moji kvizovi","Svi kvizovi","Urađeni kvizovi","Budući kvizovi","Prošli kvizovi(neurađeni)"))
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterKvizova.adapter = arrayAdapter

        kvizAdapter = KvizAdapter(kvizListViewModel.dajMojeKvizove())
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
    }

    private fun updateLista(filterNaziv: String) {
        when(filterNaziv) {
            "Svi moji kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajMojeKvizove())
                listaKvizova.adapter = kvizAdapter
            }
            "Svi kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajSveKvizove())
                listaKvizova.adapter = kvizAdapter
            }
            "Urađeni kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajUradjeneKvizove())
                listaKvizova.adapter = kvizAdapter
            }
            "Budući kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajBuduceKvizove())
                listaKvizova.adapter = kvizAdapter
            }
            "Prošli kvizovi(neurađeni)" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajNeuradjeneKvizove())
                listaKvizova.adapter = kvizAdapter
            }
        }
    }
}
