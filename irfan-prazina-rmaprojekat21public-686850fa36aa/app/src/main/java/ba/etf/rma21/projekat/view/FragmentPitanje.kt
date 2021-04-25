package ba.etf.rma21.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Pitanje

class FragmentPitanje(
        private val pitanje: Pitanje
): Fragment() {
    private lateinit var tekstPitanja: TextView
    private lateinit var listaOdgovora: ListView
    private lateinit var listaOdgovoraAdapter: OdgovorAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pitanje,container,false)

        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        listaOdgovora = view.findViewById(R.id.odgovoriLista)

        tekstPitanja.text = pitanje.tekst
        listaOdgovoraAdapter = OdgovorAdapter(view.context,android.R.layout.simple_list_item_1,pitanje.opcije,pitanje,
                false, parentFragment!!,tag!!)
        listaOdgovora.adapter = listaOdgovoraAdapter
        return view
    }

    companion object {
        fun newInstance(pitanje: Pitanje): FragmentPitanje = FragmentPitanje(pitanje)
    }
}