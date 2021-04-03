package ba.etf.rma21.projekat

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel

class SpaceItemDecoration(private val spaceHeight: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        outRect.bottom = spaceHeight
        outRect.right = spaceHeight
    }
}

class KvizAdapter(
        private val dataSet: List<Kviz>
) : RecyclerView.Adapter<KvizAdapter.ViewHolder>() {
    /**
     *Klasa za pružanje referenci na sve elemente view-a
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nazivKviza: TextView
        val slikaKviza: ImageView
        init {
            // Definisanje akcija na elemente
            nazivKviza = view.findViewById(R.id.nazivKviza)
            slikaKviza = view.findViewById(R.id.slikaKviza)
        }
    }
    // Kreiraj novi view
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Kreiraj novi view koji definiše UI elementa liste
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_kviz, viewGroup, false)
        return ViewHolder(view)
    }
    // Izmijeni sadržaj view-a
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //Pokupi element iz skupa podataka i zamijeni
        //sadržaj View sa odgovarajućim
//        viewHolder.nazivKviza = dataSet[position].naziv
//        val movieGenre = dataSet[position].
//        val context = viewHolder.slikaKviza.context
//        var id: Int = context.resources.getIdentifier(movieGenre,"drawable", context.packageName)
//        if (id == 0) viewHolder.slikaKviza.setImageResource(R.drawable.movie)
//        else viewHolder.slikaKviza.setImageResource(id)
        viewHolder.slikaKviza.setImageResource(R.drawable.plava)

//        viewHolder.itemView.setOnClickListener{ onItemClicked(dataSet[position]) }
    }
    // Vrati veličinu skupa
    override fun getItemCount() = dataSet.size
}

class MainActivity : AppCompatActivity() {
    private lateinit var filterKvizova: Spinner
    private lateinit var listaKvizova: RecyclerView
    private lateinit var kvizAdapter: KvizAdapter
    private lateinit var spinnerAdapter: SpinnerAdapter
    private var kvizListViewModel = KvizListViewModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filterKvizova = findViewById(R.id.filterKvizova)
        listaKvizova = findViewById(R.id.listaKvizova)

        val arrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
                this,R.array.vrste_kvizova, android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterKvizova.adapter = arrayAdapter

        kvizAdapter = KvizAdapter(kvizListViewModel.dajSveKvizove())
        listaKvizova.layoutManager = GridLayoutManager(this,2)
        listaKvizova.addItemDecoration(SpaceItemDecoration(5))

        listaKvizova.adapter = kvizAdapter
        kvizAdapter.notifyDataSetChanged()
    }
}

