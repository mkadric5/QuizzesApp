package ba.etf.rma21.projekat.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import java.text.SimpleDateFormat
import java.util.*

class KvizAdapter(
        private var dataSet: List<Kviz>,
        private val listener: (Kviz) -> Unit
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
            nazivKviza = view.findViewById(R.id.naziv)
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

        val context = viewHolder.slikaKviza.context
        val id: Int = context.resources.getIdentifier(bojaKviza,"drawable", context.packageName)
        viewHolder.slikaKviza.setImageResource(id)

        viewHolder.itemView.setOnClickListener{
            if (bojaKviza == "zelena" || bojaKviza == "plava")
            listener(dataSet[position])
        }
    }
    // Vrati veličinu skupa
    override fun getItemCount() = dataSet.size

    fun updateDataSet(noviKvizovi: List<Kviz>) {
        dataSet = noviKvizovi
        notifyDataSetChanged()
    }
}