package ba.etf.rma21.projekat.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel

@RequiresApi(Build.VERSION_CODES.O)
class FragmentPitanje(
        private val kvizTaken: KvizTaken?,
        private val pitanje: Pitanje,
        private val brojPitanja: Int
): Fragment() {
    private lateinit var tekstPitanja: TextView
    private lateinit var listaOdgovora: ListView
    private lateinit var listaOdgovoraAdapter: OdgovorAdapter
    private var pitanjeKvizViewModel = PitanjeKvizViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pitanje,container,false)

        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        listaOdgovora = view.findViewById(R.id.odgovoriLista)


        tekstPitanja.text = pitanje.tekstPitanja
        pitanjeKvizViewModel.popuniOdgovore(::popuniAdapter,kvizTaken!!.KvizId,pitanje.id)
//        listaOdgovoraAdapter = OdgovorAdapter(view.context,android.R.layout.simple_list_item_1,pitanje.opcije,kvizTaken,
//            pitanje, dosadasnjiOdgovori,predatKviz,false,parentFragment!!,tag!!)
//        listaOdgovora.adapter = listaOdgovoraAdapter
        return view
    }

    private fun popuniAdapter(dosadasnjiOdgovori: List<Odgovor>) {
        val toast = Toast.makeText(context,"Otvoreno pitanje", Toast.LENGTH_SHORT)
        toast.show()
        listaOdgovoraAdapter = OdgovorAdapter(view!!.context,android.R.layout.simple_list_item_1,pitanje.opcije,kvizTaken,
            pitanje, dosadasnjiOdgovori,brojPitanja,false,parentFragment!!,tag!!)
        listaOdgovora.adapter = listaOdgovoraAdapter
    }

    companion object {
        fun newInstance(kvizTaken: KvizTaken?,
                        pitanje: Pitanje,
                        predatKviz: Int): FragmentPitanje =
            FragmentPitanje(kvizTaken,pitanje,predatKviz)
    }
}