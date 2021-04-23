package ba.etf.rma21.projekat

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.view.KvizoviFragment
import ba.etf.rma21.projekat.view.PokusajFragment
import ba.etf.rma21.projekat.view.PorukaFragment
import ba.etf.rma21.projekat.view.PredmetiFragment
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
//    private lateinit var kvizoviFragment: KvizoviFragment
//    private lateinit var predmetiFragment: PredmetiFragment
//    private lateinit var porukaFragment: PorukaFragment
//    private lateinit var pokusajFragment: PokusajFragment
//    private lateinit var trenutniMainFragment: Fragment
    private var kvizListViewModel = KvizListViewModel()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_kvizovi -> {
                 val kvizoviFragment = KvizoviFragment.newInstance()
                openFragment(kvizoviFragment,"kvizovi")
                hidePokusajItems()
//                supportFragmentManager.beginTransaction().hide(trenutniMainFragment).show(kvizoviFragment).commit()
//                trenutniMainFragment = kvizoviFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_predmeti -> {
                 val predmetiFragment = PredmetiFragment.newInstance()
                openFragment(predmetiFragment,"predmeti")
                hidePokusajItems()
//                supportFragmentManager.beginTransaction().hide(trenutniMainFragment).show(predmetiFragment).commit()
//                trenutniMainFragment = predmetiFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_zaustavi -> {
                supportFragmentManager.fragments.last()
                val kvizoviFragment = KvizoviFragment.newInstance()
                openFragment(kvizoviFragment,"kvizovi")
                hidePokusajItems()
            }
            R.id.navigation_predaj -> {
                val pokusajFragment = supportFragmentManager.fragments.last() as PokusajFragment
                val kviz = pokusajFragment.dajKviz()
//                val procentTacnost = pokusajFragment.dajTacnost()
                val bodovi = pokusajFragment.dajBodove()
//                val porukaFragment = PorukaFragment.newInstance(
//                        "Završili ste kviz ${kviz.naziv} sa tačnosti ${procentTacnost}")
                kvizListViewModel.oznaciKvizKaoUradjen(kviz,bodovi)
                pokusajFragment.openporukaFragment()
                hidePokusajItems()
//                val transaction = supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.container,porukaFragment,"poruka")
//                transaction.addToBackStack(null)
//                transaction.commit()
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNav)

        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

//        bottomNavigation.setOnNavigationItemSelectedListener {item ->
//            when (item.itemId) {
//                R.id.navigation_kvizovi -> {
////                val kvizoviFragment = KvizoviFragment.newInstance()
////                openFragment(kvizoviFragment,"kvizovi")
//                    obrisiPorukaFragment()
//                    supportFragmentManager.beginTransaction().hide(trenutniMainFragment).show(kvizoviFragment).commit()
//                    trenutniMainFragment = kvizoviFragment
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.navigation_predmeti -> {
////                val predmetiFragment = PredmetiFragment.newInstance()
////                openFragment(predmetiFragment,"predmeti")
//                    obrisiPorukaFragment()
//                    supportFragmentManager.beginTransaction().hide(trenutniMainFragment).show(predmetiFragment).commit()
//                    trenutniMainFragment = predmetiFragment
//                    return@setOnNavigationItemSelectedListener true
//                }
////                R.id.navigation_predaj -> {
////
////                }
//            }
//            false
//        }
//        kvizoviFragment = KvizoviFragment.newInstance()
//        predmetiFragment = PredmetiFragment.newInstance()
//        porukaFragment = PorukaFragment.newInstance()
//        trenutniMainFragment = kvizoviFragment
//        supportFragmentManager.beginTransaction().apply {
//            add(R.id.container,kvizoviFragment).hide(kvizoviFragment)
//            add(R.id.container,predmetiFragment).hide(predmetiFragment)
//            add(R.id.container,porukaFragment).hide(porukaFragment)
//        }.commit()

        //Default fragment
        bottomNavigation.selectedItemId = R.id.navigation_kvizovi
//        val kvizoviFragment = KvizoviFragment.newInstance()
//        openFragment(kvizoviFragment,"kvizovi")

        bottomNavigation.menu.findItem(R.id.navigation_predaj).isVisible = false
        bottomNavigation.menu.findItem(R.id.navigation_zaustavi).isVisible = false
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
//        if (fragment is KvizoviFragment
//                && supportFragmentManager.findFragmentByTag("kvizovi") == null
//                || fragment is PredmetiFragment
//                && supportFragmentManager.findFragmentByTag("predmeti") == null
////                || supportFragmentManager.backStackEntryCount == 2
//        )
        val naStacku = supportFragmentManager.findFragmentByTag(tag)
        if (naStacku == null){
            transaction.replace(R.id.container,fragment,tag)
            transaction.addToBackStack(tag)
        }
//        else supportFragmentManager.popBackStackImmediate(tag,0)
        else transaction.replace(R.id.container,naStacku,tag)
        transaction.commit()
    }

    override fun onBackPressed() {
        // pokusati implementirati i opciju da izadje iz app kad se nalazi na kvizovima
//        if (supportFragmentManager.findFragmentByTag("predmeti") != null ||
//                supportFragmentManager.findFragmentByTag("pokusaj") != null){
//            hidePokusajItems()
//            bottomNavigation.selectedItemId = R.id.navigation_kvizovi
//        } else finish()
        val trenutni = supportFragmentManager.fragments.last()
        if (trenutni is KvizoviFragment)
            finish()
        else if (trenutni is PokusajFragment &&
                trenutni.getFragmentiPitanja().any {
                    it.odgovoreno()
                } && trenutni.childFragmentManager.findFragmentByTag("zavrsen kviz") == null) {
                    trenutni.vratiNaProsloPitanje()
            }
        else
            bottomNavigation.selectedItemId = R.id.navigation_kvizovi


//        if (trenutniMainFragment != kvizoviFragment)
//            bottomNavigation.selectedItemId = R.id.navigation_kvizovi
//        else finish()
    }

    fun showPokusajItems() {
        bottomNavigation.menu.findItem(R.id.navigation_predaj).isVisible = true
        bottomNavigation.menu.findItem(R.id.navigation_zaustavi).isVisible = true
        bottomNavigation.menu.findItem(R.id.navigation_kvizovi).isVisible = false
        bottomNavigation.menu.findItem(R.id.navigation_predmeti).isVisible = false
    }

    fun hidePokusajItems() {
        bottomNavigation.menu.findItem(R.id.navigation_predaj).isVisible = false
        bottomNavigation.menu.findItem(R.id.navigation_zaustavi).isVisible = false
        bottomNavigation.menu.findItem(R.id.navigation_kvizovi).isVisible = true
        bottomNavigation.menu.findItem(R.id.navigation_predmeti).isVisible = true
    }

//    fun showPorukaFragment(poruka: String) {
//        porukaFragment.setPorukaFragmentText(poruka)
//        supportFragmentManager.beginTransaction().hide(trenutniMainFragment).show(porukaFragment).commit()
//        trenutniMainFragment = porukaFragment
//        kvizoviFragment.osvjeziKvizove()
//    }
//
//    fun showPokusajFragment(fragment: PokusajFragment) {
//        pokusajFragment = fragment
//        supportFragmentManager.beginTransaction().apply{
//            add(R.id.container,pokusajFragment,"pokusaj")
//            hide(trenutniMainFragment).show(pokusajFragment)
//        }.commit()
//        trenutniMainFragment = pokusajFragment
//    }
}