package ba.etf.rma21.projekat

import android.accounts.Account
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.repositories.*
import ba.etf.rma21.projekat.view.FragmentKvizovi
import ba.etf.rma21.projekat.view.FragmentPokusaj
import ba.etf.rma21.projekat.view.FragmentPredmeti
import ba.etf.rma21.projekat.viewmodel.UpisPredmetViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private var upisPredmetViewModel = UpisPredmetViewModel()

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
                pokusajFragment.openPorukaZavrsenKviz()
                hidePokusajItems()
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNav)

        AccountRepository.setContext(applicationContext)
        DBRepository.setContext(applicationContext)
        KvizRepository.setContext(applicationContext)
        OdgovorRepository.setContext(applicationContext)
        PitanjeKvizRepository.setContext(applicationContext)
        PredmetIGrupaRepository.setContext(applicationContext)
        TakeKvizRepository.setContext(applicationContext)

        val payload = intent.getStringExtra("payload")
        if (payload != null)
        upisPredmetViewModel.postaviAccHash(payload,applicationContext)

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
            return
        else if (trenutni is FragmentPokusaj) {
                    trenutni.vratiNaPrethodno()
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

    fun vratiNaKvizove() {
        bottomNavigation.selectedItemId = R.id.kvizovi
    }
}