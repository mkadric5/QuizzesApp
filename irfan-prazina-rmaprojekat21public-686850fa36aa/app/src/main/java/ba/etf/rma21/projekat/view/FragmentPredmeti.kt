package ba.etf.rma21.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.viewmodel.UpisPredmetViewModel

class FragmentPredmeti: Fragment() {
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirPredmet: Spinner
    private lateinit var odabirGrupa: Spinner
    private lateinit var dodajPredmetButton: Button
    private lateinit var odabirGodinaAdapter: ArrayAdapter<String>
    private lateinit var odabirPredmetAdapter: ArrayAdapter<String>
    private lateinit var odabirGrupaAdapter: ArrayAdapter<String>
    private var upisPredmetViewModel = UpisPredmetViewModel()
    private var lastGodina: Int = 0
    private var lastPredmet: Int = 0
    private var lastGrupa: Int = 0

    override fun onDestroyView() {
        super.onDestroyView()
        lastGodina = odabirGodina.selectedItemPosition
        lastPredmet = odabirPredmet.selectedItemPosition
        lastGrupa = odabirGrupa.selectedItemPosition
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.predmeti_fragment, container, false)

        odabirGodina = view.findViewById(R.id.odabirGodina)
        odabirPredmet = view.findViewById(R.id.odabirPredmet)
        odabirGrupa = view.findViewById(R.id.odabirGrupa)
        dodajPredmetButton = view.findViewById(R.id.dodajPredmetDugme)

        odabirGodinaAdapter = ArrayAdapter(odabirGodina.context, android.R.layout.simple_spinner_item,
            listOf("1", "2", "3", "4", "5"))
        odabirGodinaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        odabirGodina.adapter = odabirGodinaAdapter

        odabirGodina.setSelection(lastGodina)


//        odabirGodina.setSelection(odabirGodinaAdapter.getPosition(intent.getStringExtra("default_godina")))

        odabirGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                popuniPredmeteZaGodinu(odabirGodina.selectedItem.toString())
                odabirPredmet.setSelection(lastPredmet)
                // ako nema predmeta za datu godinu, onda nema
                // ni grupa
                if (odabirPredmet.count == 0){
                    dodajPredmetButton.isEnabled = false
                    popuniGrupeZaPredmet("")
                    odabirGrupa.setSelection(lastGrupa)
                } else dodajPredmetButton.isEnabled = true
            }
        }

        odabirPredmet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                popuniGrupeZaPredmet(odabirPredmet.selectedItem.toString())
                odabirGrupa.setSelection(lastGrupa)
                dodajPredmetButton.isEnabled = odabirGrupa.count != 0
            }
        }

        dodajPredmetButton.setOnClickListener{
            val godinaItem = odabirGodina.selectedItem
            val predmetItem = odabirPredmet.selectedItem
            val grupaItem = odabirGrupa.selectedItem
            if (godinaItem != null && predmetItem != null && grupaItem != null) {
                upisPredmetViewModel.upisiKorisnika(grupaItem.toString(),predmetItem.toString())
//                (activity as MainActivity).showPorukaFragment(
//                        "Uspješno ste upisani u grupu ${grupaItem} predmeta ${predmetItem}!")
                val porukaFragment = FragmentPoruka.newInstance(
                        "Uspješno ste upisani u grupu $grupaItem predmeta ${predmetItem}!")
                openPorukaFragment(porukaFragment)
            }
        }
        return view
    }

    companion object {
        fun newInstance(): FragmentPredmeti = FragmentPredmeti()
    }

    private fun openPorukaFragment(porukaFragment: Fragment) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,porukaFragment,"poruka")
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun popuniGrupeZaPredmet(predmet: String) {
        odabirGrupaAdapter = ArrayAdapter(odabirGodina.context, android.R.layout.simple_spinner_item,
            upisPredmetViewModel.dajGrupeZaPredmet(predmet))
        odabirGrupaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        odabirGrupa.adapter = odabirGrupaAdapter
    }

    private fun popuniPredmeteZaGodinu(godina: String) {
        val brGodine = Integer.parseInt(godina)
        odabirPredmetAdapter = ArrayAdapter(odabirGodina.context, android.R.layout.simple_spinner_item,
            upisPredmetViewModel.dajOPredmeteZaGodinu(brGodine))
        odabirPredmetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        odabirPredmet.adapter = odabirPredmetAdapter
    }
}