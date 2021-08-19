package ba.etf.rma21.projekat.view

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel

@RequiresApi(Build.VERSION_CODES.O)
class OdgovorAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val elements: List<String>,
                     private val kvizTaken: KvizTaken?, private val pitanje: Pitanje, private val dosadasnjiOdgovor: List<Odgovor>,
                     private var brojPitanja: Int,
                     private var needToBeDisabled: Boolean, private var pokusajFragment: Fragment, private var tag: String):
        ArrayAdapter<String>(context, layoutResource, elements) {
    private val pitanjaKvizViewModel = PitanjeKvizViewModel()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val tekstOdgovor = view.findViewById<TextView>(android.R.id.text1)

//        val pitanjeKviz = pitanjaKvizViewModel.dajPitanjeKvizZaPitanje(pitanje)
//        val kviz = kvizListViewModel.dajKviz(pitanjeKviz.kviz)

//        if (kviz.datumRada != null && kviz.osvojeniBodovi != null) {
//            if (tekstOdgovor.text == elements[pitanje.tacan])
//                tekstOdgovor.setBackgroundColor(Color.parseColor("#3DDC84"))
//            needToBeDisabled = true
//        }

//        if (pitanjeKviz.odgovor != null && pitanjeKviz.odgovor != -1) {
//            if (tekstOdgovor.text == elements[pitanjeKviz.odgovor!!]) {
//                if (tekstOdgovor.text != pitanje.opcije[pitanje.tacan])
//                    tekstOdgovor.setBackgroundColor(Color.parseColor("#DB4F3D"))
////                needToBeDisabled = true
//            }
//
//            if (tekstOdgovor.text == elements[pitanje.tacan])
//                tekstOdgovor.setBackgroundColor(Color.parseColor("#3DDC84"))
//            needToBeDisabled = true
//        }

            //Da li je odgovoreno na pitanje?
            if (dosadasnjiOdgovor.isNotEmpty()) {
                val odgovorPrijasnji = dosadasnjiOdgovor[0]
                if (odgovorPrijasnji.odgovoreno < pitanje.opcije.size && tekstOdgovor.text == elements[odgovorPrijasnji.odgovoreno]) {
                    if (tekstOdgovor.text != pitanje.opcije[pitanje.tacan]) {
                        tekstOdgovor.setBackgroundColor(Color.parseColor("#DB4F3D"))
                        (pokusajFragment as FragmentPokusaj).postaviBojuZaNavigacijaPitanje(
                            tag.toInt() - 1, false
                        )
                    }
                     else  (pokusajFragment as FragmentPokusaj).postaviBojuZaNavigacijaPitanje(
                        tag.toInt() - 1, true
                    )
                }
                else if (odgovorPrijasnji.odgovoreno == pitanje.opcije.size)
                    (pokusajFragment as FragmentPokusaj).postaviBojuZaNavigacijaPitanje(
                        tag.toInt() - 1, false
                    )
                if(tekstOdgovor.text == elements[pitanje.tacan])
                    tekstOdgovor.setBackgroundColor(Color.parseColor("#3DDC84"))
                needToBeDisabled = true
            }


        view.setOnClickListener {
            var tacnoOdgovoreno = tekstOdgovor.text == pitanje.opcije[pitanje.tacan]
            if (!needToBeDisabled) {
                if (!tacnoOdgovoreno) {
                    tekstOdgovor.setBackgroundColor(Color.parseColor("#DB4F3D"))
                }
                (pokusajFragment as FragmentPokusaj).postaviBojuZaNavigacijaPitanje(
                    tag.toInt() - 1, tacnoOdgovoreno
                )

                parent.getChildAt(pitanje.tacan).setBackgroundColor(Color.parseColor("#3DDC84"))
                pitanjaKvizViewModel.postaviOdgovor(
                    kvizTaken!!, pitanje.id, parent.indexOfChild(it))
                needToBeDisabled = true
                }
            }

        return view
    }
        }