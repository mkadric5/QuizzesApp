package ba.etf.rma21.projekat.view

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel
import com.google.android.material.navigation.NavigationView

class FragmentPokusaj(
    private var kvizTaken: KvizTaken?,
    private var pitanja: List<Pitanje>,
    private var predatKviz: Boolean
): Fragment() {
    private lateinit var navigacijaPitanja: NavigationView
    private var pitanjaKvizViewModel = PitanjeKvizViewModel()
    private var kvizListViewModel = KvizListViewModel()

    fun vratiNaProsloPitanje() {
        var i = dajIndexTrenutnogPitanja() - 1
        if (i == -1) i++
        openPitanjeFragment(pitanja[i])
    }

    private fun dajIndexTrenutnogPitanja(): Int {
        val trenutni = childFragmentManager.fragments.last()
        if (trenutni is FragmentPoruka)
            return pitanja.size
        return trenutni.tag!!.toInt() - 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pokusaj,container,false)

        navigacijaPitanja = view.findViewById(R.id.navigacijaPitanja)
        var i = 0
        while(i<pitanja.size) {
            navigacijaPitanja.menu.add(R.id.grupaItem,i,i,"${i+1}")
            i++
        }
        navigacijaPitanja.menu.add(R.id.grupaItem,i,i,"Rezultat")

        navigacijaPitanja.menu.getItem(pitanja.size).isVisible = predatKviz

//        pitanjaKvizViewModel.otvoriPitanje(::openPitanjeFragment,pitanja[0],kvizTaken!!.KvizId)
        openPitanjeFragment(pitanja[0])

//        for (element in pitanja){
//            if (pitanjaOdgovorena()[pitanja.indexOf(element)])
//            postaviBojuZaNavigacijaPitanje(pitanja.indexOf(element),pitanjaKvizViewModel.tacnoOdgovoreno(element))
//        }


        navigacijaPitanja.setNavigationItemSelectedListener {
            if (it != navigacijaPitanja.menu.getItem(pitanja.size)){
                val indeks = Integer.parseInt(it.toString())
//                val pitanjeFragment = FragmentPitanje.newInstance(kvizTaken,pitanja[indeks-1],
//                dosadasnjiOdgovori.filter { o -> o.pitanjeId == pitanja[indeks-1].id },
//                    dosadasnjiOdgovori.size == pitanja.size)
//                openPitanjeFragment(pitanjeFragment,it.toString())
//                pitanjaKvizViewModel.otvoriPitanje(::openPitanjeFragment,pitanja[indeks-1],
//                                                    kvizTaken!!.KvizId)
                openPitanjeFragment(pitanja[indeks-1])
            }
            else {
                pitanjaKvizViewModel.otvoriPorukuZavrsenKviz(::openPorukaFragment,kvizTaken!!.KvizId)
            }
            true
        }
        return view
    }

     fun postaviBojuZaNavigacijaPitanje(itemIndex: Int, tacnoOdgovoreno: Boolean) {
         val menuItem = navigacijaPitanja.menu.getItem(itemIndex)
         var color = Color.parseColor("#3DDC84")
        if (!tacnoOdgovoreno)
            color = Color.parseColor("#DB4F3D")
        val spanString = SpannableString(menuItem.title.toString())
        spanString.setSpan(ForegroundColorSpan(color), 0, spanString.length, 0)
        menuItem.title = spanString
    }

     private fun openPitanjeFragmentInstance(pitanjeFragment: Fragment, tag: String) {
        val transaction = childFragmentManager.beginTransaction()
         val naStacku = childFragmentManager.findFragmentByTag(tag)
         if (naStacku == null){
             transaction.replace(R.id.framePitanje,pitanjeFragment,tag)
             transaction.addToBackStack(tag)
         }
         else
             transaction.replace(R.id.framePitanje,naStacku,tag)
         transaction.commit()
    }

    private fun openPitanjeFragment(pitanje: Pitanje) {
        val pitanjeFragment = FragmentPitanje.newInstance(kvizTaken,pitanje,pitanja.size)
        openPitanjeFragmentInstance(pitanjeFragment,(pitanja.indexOf(pitanje)+1).toString())
    }

    private fun openPorukaFragment(kviz: Kviz?) {
        val tacnost = kvizTaken!!.osvojeniBodovi
        val porukaFragment = FragmentPoruka.newInstance(
                "Završili ste kviz ${kviz!!.naziv} sa tačnosti $tacnost")
        val tag = "rezultat"

        val transaction = childFragmentManager.beginTransaction()
        val naStacku = childFragmentManager.findFragmentByTag(tag)
        if (naStacku == null){
            transaction.replace(R.id.framePitanje,porukaFragment,tag)
            transaction.addToBackStack(tag)
        }
        else transaction.replace(R.id.framePitanje,naStacku,tag)
        transaction.commit()
        navigacijaPitanja.menu.getItem(pitanja.size).isVisible = true
    }

//    private fun dajTacnost(): String {
//        return (dajBrojTacnih().toFloat()/pitanja.size * 100).toString() + "%"
//    }

//    private fun dajNazivKviza(): String {
//        return pitanjaKvizViewModel.dajPitanjeKvizZaPitanje(pitanja[0]).kviz
//    }

//    fun dajKviz(): Kviz {
//        return kvizListViewModel.dajKviz(dajNazivKviza())
//    }

//    private fun dajBrojTacnih(): Int {
//        return pitanja.map{
//            p -> pitanjaKvizViewModel.tacnoOdgovoreno(p)
//        }.count { o -> o }
//    }

//    fun dajBodove(): Float {
//        return dajBrojTacnih().toFloat()
//    }

//    fun pitanjaOdgovorena(): List<Boolean> {
//        return pitanja.map {
//            p -> pitanjaKvizViewModel.odgovoreno(p)
//        }
//    }

    fun openPorukaZavrsenKviz() {
        pitanjaKvizViewModel.zavrsiKvizOtvoriPoruku(::openPorukaFragment,kvizTaken!!,pitanja)
    }

    fun vratiNaPrethodno() {
        val actionFragKvizovi  = fun () {
            (activity as MainActivity).vratiNaKvizove()
        }
        pitanjaKvizViewModel.vratiPrethodniFragment(::vratiNaProsloPitanje,actionFragKvizovi,
                                                    pitanja,kvizTaken!!)
    }

    companion object {
        fun newInstance(kvizTaken: KvizTaken?, pitanja: List<Pitanje>, predatKviz: Boolean): FragmentPokusaj =
            FragmentPokusaj(kvizTaken,pitanja,predatKviz)
    }
}