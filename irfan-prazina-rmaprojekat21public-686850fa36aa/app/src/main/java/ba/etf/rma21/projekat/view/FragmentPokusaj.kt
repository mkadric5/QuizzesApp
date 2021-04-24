package ba.etf.rma21.projekat.view

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel
import com.google.android.material.navigation.NavigationView

class FragmentPokusaj(
    private var pitanja: List<Pitanje>
): Fragment() {
    private lateinit var navigacijaPitanja: NavigationView
    private var fragmentiPitanja = mutableListOf<FragmentPitanje>()
    private lateinit var trenutnoPitanjeFragment: Fragment
    private var pitanjaKvizViewModel = PitanjeKvizViewModel()
    private var kvizListViewModel = KvizListViewModel()

    fun vratiNaProsloPitanje() {
        var i = dajIndexTrenutnogPitanja() - 1
        if (i == -1) i++
        val pitanjeFragment = FragmentPitanje.newInstance(pitanja[i])
        val tag = (i+1).toString()
        openPitanjeFragment(pitanjeFragment,tag)
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
        var i: Int = 0
        while(i<pitanja.size) {
            navigacijaPitanja.menu.add(R.id.grupaItem,i,i,"${i+1}")
            i++
        }
        navigacijaPitanja.menu.add(R.id.grupaItem,i,i,"Rezultat")

        if (dajKviz().datumRada == null && dajKviz().osvojeniBodovi == null)
        navigacijaPitanja.menu.getItem(pitanja.size).isVisible = false


//        pitanja.forEach {
//            fragmentiPitanja.add(FragmentPitanje.newInstance(it))
//        }
//        childFragmentManager.beginTransaction().apply {
//            fragmentiPitanja.forEach{
//                add(R.id.framePitanje,it).hide(it)
//            }
//        }.commit()
//        trenutnoPitanjeFragment = fragmentiPitanja[0]
//        childFragmentManager.beginTransaction().show(trenutnoPitanjeFragment).commit()

        val pocetniFragment = FragmentPitanje.newInstance(pitanja[0])
        openPitanjeFragment(pocetniFragment,"1")

        for (element in pitanja){
            if (pitanjaOdgovorena()[pitanja.indexOf(element)])
            setTextColorForMenuItem(pitanja.indexOf(element),pitanjaKvizViewModel.tacnoOdgovoreno(element))
        }


        navigacijaPitanja.setNavigationItemSelectedListener {
            if (it != navigacijaPitanja.menu.getItem(pitanja.size)){
                val indeks = Integer.parseInt(it.toString())
//                val pitanjeFragment = fragmentiPitanja[indeks-1]
                val pitanjeFragment = FragmentPitanje.newInstance(pitanja[indeks-1])
                openPitanjeFragment(pitanjeFragment,it.toString())
//            setTextColorForMenuItem(it,true)
            }
            else {
                openporukaFragment()
            }
            true
        }
        return view
    }

     fun setTextColorForMenuItem(itemIndex: Int, tacnoOdgovoreno: Boolean) {
         val menuItem = navigacijaPitanja.menu.getItem(itemIndex)
         var color = Color.parseColor("#3DDC84")
        if (!tacnoOdgovoreno)
            color = Color.parseColor("#DB4F3D")
        val spanString = SpannableString(menuItem.title.toString())
        spanString.setSpan(ForegroundColorSpan(color), 0, spanString.length, 0)
        menuItem.title = spanString
    }

    fun getNavigacijaPitanja(): NavigationView {
        return navigacijaPitanja
    }

     private fun openPitanjeFragment(pitanjeFragment: Fragment, tag: String) {
        val transaction = childFragmentManager.beginTransaction()
//         transaction.hide(trenutnoPitanjeFragment)
//        transaction.show(pitanjeFragment)
//        transaction.commit()
//        trenutnoPitanjeFragment = pitanjeFragment

         val naStacku = childFragmentManager.findFragmentByTag(tag)
         if (naStacku == null){
             transaction.replace(R.id.framePitanje,pitanjeFragment,tag)
             transaction.addToBackStack(tag)
         }
         else
             transaction.replace(R.id.framePitanje,naStacku,tag)
         transaction.commit()

//        val postojeciNaStacku = childFragmentManager.findFragmentByTag(indeks)
//        if (postojeciNaStacku != null){
//            transaction.replace(R.id.framePitanje,postojeciNaStacku,indeks)
//            transaction.commit()
//        }
//        else {
//            transaction.replace(R.id.framePitanje,fragment,indeks)
//            transaction.addToBackStack(indeks)
//            transaction.commit()
//        }
    }

    fun openporukaFragment() {
        val porukaFragment = FragmentPoruka.newInstance(
                "Završili ste kviz ${dajNazivKviza()} sa tačnosti ${dajTacnost()}")
        val tag = "rezultat"

        val transaction = childFragmentManager.beginTransaction()
        val naStacku = childFragmentManager.findFragmentByTag(tag)
//        transaction.hide(trenutnoPitanjeFragment)
        if (naStacku == null){
            transaction.replace(R.id.framePitanje,porukaFragment,tag)
            transaction.addToBackStack(tag)
//            transaction.add(R.id.framePitanje,porukaFragment,"rezultat")
//            trenutnoPitanjeFragment = porukaFragment
        }
        else{
            transaction.replace(R.id.framePitanje,naStacku,tag)
//            transaction.show(naStacku)
//            trenutnoPitanjeFragment = naStacku
        }
        transaction.commit()
        navigacijaPitanja.menu.getItem(pitanja.size).isVisible = true
    }

    private fun dajTacnost(): String {
        return (dajBrojTacnih().toFloat()/pitanja.size * 100).toString() + "%"
    }

    private fun dajNazivKviza(): String {
        return pitanjaKvizViewModel.dajPitanjeKvizZaPitanje(pitanja[0]).kviz
    }

    fun dajKviz(): Kviz {
        return kvizListViewModel.dajKviz(dajNazivKviza())
    }

    private fun dajBrojTacnih(): Int {
        return pitanja.map{
            p -> pitanjaKvizViewModel.tacnoOdgovoreno(p)
        }.count { o -> o }
    }

    fun dajBodove(): Float {
        return dajBrojTacnih().toFloat()
    }

    fun pitanjaOdgovorena(): List<Boolean> {
        return pitanja.map {
            p -> pitanjaKvizViewModel.odgovoreno(p)
        }
    }

    companion object {
        fun newInstance(pitanja: List<Pitanje>): FragmentPokusaj = FragmentPokusaj(pitanja)
    }
}