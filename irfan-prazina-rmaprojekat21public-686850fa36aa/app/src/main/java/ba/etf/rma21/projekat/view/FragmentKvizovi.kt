package ba.etf.rma21.projekat.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel
import java.util.*
@RequiresApi(Build.VERSION_CODES.O)
class FragmentKvizovi: Fragment() {
    private lateinit var filterKvizova: Spinner
    private lateinit var listaKvizova: RecyclerView
    private lateinit var kvizAdapter: KvizAdapter
    private lateinit var filterKvizovaAdapter: ArrayAdapter<String>
    private var kvizListViewModel = KvizListViewModel()
    private var pitanjaKvizViewModel = PitanjeKvizViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_kvizovi, container, false)

        filterKvizova = view.findViewById(R.id.filterKvizova)
        listaKvizova = view.findViewById(R.id.listaKvizova)


        filterKvizovaAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item,
                listOf("Svi moji kvizovi","Svi kvizovi","Urađeni kvizovi","Budući kvizovi","Prošli kvizovi"))
        filterKvizovaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterKvizova.adapter = filterKvizovaAdapter

        kvizAdapter = KvizAdapter(listOf()){ kviz->
            pitanjaKvizViewModel.otvoriPokusaj(::openPokusajFragment,kviz)
        }
        listaKvizova.layoutManager = GridLayoutManager(context!!,2)
        listaKvizova.addItemDecoration(SpaceItemDecoration(5))
        listaKvizova.adapter = kvizAdapter
//        kvizListViewModel.showKvizes(::updateKvizove,"Svi moji kvizovi")
        //kvizAdapter.notifyDataSetChanged()



        filterKvizova.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val toast = Toast.makeText(context,"Dohvacanje kvizova",Toast.LENGTH_SHORT)
                toast.show()
                kvizListViewModel.showKvizes(::updateKvizove,filterKvizova.selectedItem.toString())
            }
        }

        return view
    }

    private fun openPokusajFragmentInstance(fragment: Fragment, tag: String) {
        val supportFragmentManager = (activity as AppCompatActivity).supportFragmentManager
        val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container,fragment,tag)
        transaction.commit()
    }

    private fun openPokusajFragment(kvizTaken: KvizTaken?, pitanja: List<Pitanje>,kviz: Kviz) {
        if (filterKvizova.selectedItem.toString() != "Svi kvizovi") {
            val toast = Toast.makeText(context,"Otvaranje kviza",Toast.LENGTH_SHORT)
            toast.show()
            val pokusajFragment = FragmentPokusaj.newInstance(kvizTaken, pitanja,kviz.predat)
            openPokusajFragmentInstance(pokusajFragment, kviz.naziv)
            if (kviz.predat)
                (activity as MainActivity).hidePokusajItems()
            else {
                    Log.e("INFO O KVIZU",kviz.toString())
                (activity as MainActivity).showPokusajItems()
            }
        }
    }

    companion object {
        fun newInstance(): FragmentKvizovi = FragmentKvizovi()
    }

    private fun updateKvizove(noviKvizovi: List<Kviz>) {
        val toast = Toast.makeText(context,"Kvizovi dohvaceni",Toast.LENGTH_SHORT)
        toast.show()
        kvizAdapter.updateDataSet(noviKvizovi)
    }
}