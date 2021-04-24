package ba.etf.rma21.projekat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.view.FragmentKvizovi
import ba.etf.rma21.projekat.view.FragmentPokusaj
import ba.etf.rma21.projekat.view.FragmentPredmeti
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private var kvizListViewModel = KvizListViewModel()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.kvizovi -> {
                 val kvizoviFragment = FragmentKvizovi.newInstance()
                openFragment(kvizoviFragment,"kvizovi")
                hidePokusajItems()
                return@OnNavigationItemSelectedListener true
            }
            R.id.predmeti -> {
                 val predmetiFragment = FragmentPredmeti.newInstance()
                openFragment(predmetiFragment,"predmeti")
                hidePokusajItems()
                return@OnNavigationItemSelectedListener true
            }
            R.id.zaustaviKviz -> {
//                supportFragmentManager.fragments.last()
                val kvizoviFragment = FragmentKvizovi.newInstance()
                openFragment(kvizoviFragment,"kvizovi")
                hidePokusajItems()
            }
            R.id.predajKviz -> {
                val pokusajFragment = supportFragmentManager.fragments.last() as FragmentPokusaj
                val kviz = pokusajFragment.dajKviz()
                val bodovi = pokusajFragment.dajBodove()
                kvizListViewModel.oznaciKvizKaoUradjen(kviz,bodovi)
                pokusajFragment.openporukaFragment()
                hidePokusajItems()
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNav)

        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //Default fragment
        bottomNavigation.selectedItemId = R.id.kvizovi
//        val kvizoviFragment = KvizoviFragment.newInstance()
//        openFragment(kvizoviFragment,"kvizovi")

        bottomNavigation.menu.findItem(R.id.predajKviz).isVisible = false
        bottomNavigation.menu.findItem(R.id.zaustaviKviz).isVisible = false
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
                else
                    transaction.replace(R.id.container,naStacku,tag)
                transaction.commit()
//        else supportFragmentManager.popBackStackImmediate(tag,0)
//        else {
//            transaction.replace(R.id.container,naStacku,tag)
//        }
//        transaction.commit()
    }

    override fun onBackPressed() {
        // pokusati implementirati i opciju da izadje iz app kad se nalazi na kvizovima
//        if (supportFragmentManager.findFragmentByTag("predmeti") != null ||
//                supportFragmentManager.findFragmentByTag("pokusaj") != null){
//            hidePokusajItems()
//            bottomNavigation.selectedItemId = R.id.navigation_kvizovi
//        } else finish()
        val trenutni = supportFragmentManager.fragments.last()
        if (trenutni is FragmentKvizovi)
            finish()
        else if (trenutni is FragmentPokusaj &&
                trenutni.getFragmentiPitanja().any {
                    it.odgovoreno()
                } && trenutni.childFragmentManager.findFragmentByTag("zavrsen kviz") == null) {
                    trenutni.vratiNaProsloPitanje()
            }
        else
            bottomNavigation.selectedItemId = R.id.kvizovi


//        if (trenutniMainFragment != kvizoviFragment)
//            bottomNavigation.selectedItemId = R.id.navigation_kvizovi
//        else finish()
    }

    fun showPokusajItems() {
        bottomNavigation.menu.findItem(R.id.predajKviz).isVisible = true
        bottomNavigation.menu.findItem(R.id.zaustaviKviz).isVisible = true
        bottomNavigation.menu.findItem(R.id.kvizovi).isVisible = false
        bottomNavigation.menu.findItem(R.id.predmeti).isVisible = false
    }

    fun hidePokusajItems() {
        bottomNavigation.menu.findItem(R.id.predajKviz).isVisible = false
        bottomNavigation.menu.findItem(R.id.zaustaviKviz).isVisible = false
        bottomNavigation.menu.findItem(R.id.kvizovi).isVisible = true
        bottomNavigation.menu.findItem(R.id.predmeti).isVisible = true
    }
}