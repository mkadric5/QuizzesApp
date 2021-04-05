package ba.etf.rma21.projekat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ba.etf.rma21.projekat.viewmodel.UpisPredmetaViewModel


class UpisPredmet : AppCompatActivity() {
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirPredmet: Spinner
    private lateinit var odabirGrupa: Spinner
    private lateinit var dodajPredmetButton: Button
    private lateinit var odabirGodinaAdapter: ArrayAdapter<String>
    private lateinit var odabirPredmetAdapter: ArrayAdapter<String>
    private lateinit var odabirGrupaAdapter: ArrayAdapter<String>
    private var upisPredmetaViewModel = UpisPredmetaViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_predmet)

        odabirGodina = findViewById(R.id.odabirGodina)
        odabirPredmet = findViewById(R.id.odabirPredmet)
        odabirGrupa = findViewById(R.id.odabirGrupa)
        dodajPredmetButton = findViewById(R.id.dodajPredmetDugme)

        odabirGodinaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
                listOf("1", "2", "3", "4", "5"))
        odabirGodinaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        odabirGodina.adapter = odabirGodinaAdapter

        odabirGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                popuniPredmeteZaGodinu(odabirGodina.selectedItem.toString())
            }
        }

        odabirPredmet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                popuniGrupeZaPredmet(odabirPredmet.selectedItem.toString())
            }
        }

        dodajPredmetButton.setOnClickListener{
            val godinaItem = odabirGodina.selectedItem
            val predmetItem = odabirPredmet.selectedItem
            val grupaItem = odabirGrupa.selectedItem
            if (godinaItem != null && predmetItem != null && grupaItem != null) {
                val returnIntent = Intent()
                returnIntent.putExtra("predmet",predmetItem.toString())
                returnIntent.putExtra("grupa",grupaItem.toString())
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }

    private fun popuniGrupeZaPredmet(predmet: String) {
        odabirGrupaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
        upisPredmetaViewModel.dajGrupeZaPredmet(predmet))
        odabirGrupaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        odabirGrupa.adapter = odabirGrupaAdapter
    }

    private fun popuniPredmeteZaGodinu(godina: String) {
        val brGodine = Integer.parseInt(godina)
        odabirPredmetAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
                upisPredmetaViewModel.dajPredmeteZaGodinu(brGodine))
        odabirPredmetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        odabirPredmet.adapter = odabirPredmetAdapter
    }
}