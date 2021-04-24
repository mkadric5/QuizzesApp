package ba.etf.rma21.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel

class FragmentKvizovi: Fragment() {
    private lateinit var filterKvizova: Spinner
    private lateinit var listaKvizova: RecyclerView
    private lateinit var kvizAdapter: KvizAdapter
    private lateinit var filterKvizovaAdapter: ArrayAdapter<String>
    private var kvizListViewModel = KvizListViewModel()
    private var pitanjaKvizViewModel = PitanjeKvizViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.kvizovi_fragment, container, false)

        filterKvizova = view.findViewById(R.id.filterKvizova)
        listaKvizova = view.findViewById(R.id.listaKvizova)


        filterKvizovaAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item,
                listOf("Svi moji kvizovi","Svi kvizovi","Urađeni kvizovi","Budući kvizovi","Prošli kvizovi"))
        filterKvizovaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterKvizova.adapter = filterKvizovaAdapter

        kvizAdapter = KvizAdapter(kvizListViewModel.dajMojeKvizove()) {kviz ->
                val pokusajFragment = FragmentPokusaj.newInstance(
                        pitanjaKvizViewModel.dajPitanjaZaKviz(kviz))
                openPokusajFragment(pokusajFragment, kviz.naziv)
            if (kviz.datumRada == null && kviz.osvojeniBodovi == null)
                (activity as MainActivity).showPokusajItems()
            else (activity as MainActivity).hidePokusajItems()
        }
        listaKvizova.layoutManager = GridLayoutManager(context!!,2)
        listaKvizova.addItemDecoration(SpaceItemDecoration(5))
        listaKvizova.adapter = kvizAdapter
        //kvizAdapter.notifyDataSetChanged()



        filterKvizova.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateLista(filterKvizova.selectedItem.toString())
            }
        }

        return view
    }

    private fun openPokusajFragment(fragment: Fragment, tag: String) {
        val supportFragmentManager = (activity as AppCompatActivity).supportFragmentManager
        val transaction = supportFragmentManager.beginTransaction()

//        val naStacku = supportFragmentManager.findFragmentByTag(tag)
//        if (naStacku == null){
            transaction.replace(R.id.container,fragment,tag)
//            transaction.addToBackStack(tag)
//        }
//        else transaction.replace(R.id.container,fragment,tag)
        transaction.commit()
//        (activity as MainActivity).showPokusajFragment(fragment as PokusajFragment)
    }

    companion object {
        fun newInstance(): FragmentKvizovi = FragmentKvizovi()
    }

    private fun updateLista(filterNaziv: String) {
        when(filterNaziv) {
            "Svi moji kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajMojeKvizove())
            }
            "Svi kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajSveKvizove())
            }
            "Urađeni kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajMojeUradjeneKvizove())
            }
            "Budući kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajMojeBuduceKvizove())
            }
            "Prošli kvizovi" -> {
                kvizAdapter.updateDataSet(kvizListViewModel.dajMojeNeuradjeneKvizove())
            }
        }
    }
}