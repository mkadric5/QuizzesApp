package ba.etf.rma21.projekat.view

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel

class OdgovorAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val elements: List<String>,
private val pitanje: Pitanje,private var needToBeDisabled: Boolean):
        ArrayAdapter<String>(context, layoutResource, elements) {
    private val pitanjaKvizViewModel = PitanjeKvizViewModel()
    private val kvizListViewModel = KvizListViewModel()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val tekstOdgovor = view.findViewById<TextView>(android.R.id.text1)
        val pitanjeKviz = pitanjaKvizViewModel.dajPitanjeKvizZaPitanje(pitanje)
        val kviz = kvizListViewModel.dajKviz(pitanjeKviz.kviz)

        if (kviz.datumRada != null && kviz.osvojeniBodovi != null) {
            if (tekstOdgovor.text == elements[pitanje.tacan])
                tekstOdgovor.setBackgroundColor(Color.parseColor("#3DDC84"))
            needToBeDisabled = true
        }

        if (pitanjeKviz.odgovor != null) {
            if (tekstOdgovor.text == elements[pitanjeKviz.odgovor!!]) {
                if (tekstOdgovor.text != pitanje.opcije[pitanje.tacan])
                    tekstOdgovor.setBackgroundColor(Color.parseColor("#DB4F3D"))
                needToBeDisabled = true
            }
            if (tekstOdgovor.text == elements[pitanje.tacan])
                tekstOdgovor.setBackgroundColor(Color.parseColor("#3DDC84"))
            needToBeDisabled = true
        }

        view.setOnClickListener {
            if (!needToBeDisabled) {
                if (tekstOdgovor.text != pitanje.opcije[pitanje.tacan])
                    tekstOdgovor.setBackgroundColor(Color.parseColor("#DB4F3D"))
                parent.getChildAt(pitanje.tacan).setBackgroundColor(Color.parseColor("#3DDC84"))
                pitanjaKvizViewModel.postaviOdgovor(pitanje,parent.indexOfChild(it))
                needToBeDisabled = true
            }
        }

        return view
    }

    fun odgovoreno(): Boolean {
        val pitanjeKviz = pitanjaKvizViewModel.dajPitanjeKvizZaPitanje(pitanje)
        return pitanjeKviz.odgovor != null
    }

    fun tacnoOdgovoreno(): Boolean {
        val pitanjeKviz = pitanjaKvizViewModel.dajPitanjeKvizZaPitanje(pitanje)
        return pitanjeKviz.odgovor == pitanje.tacan
    }
        }