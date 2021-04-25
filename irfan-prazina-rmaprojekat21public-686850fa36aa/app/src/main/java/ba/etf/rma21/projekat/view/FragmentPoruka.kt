package ba.etf.rma21.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R

class FragmentPoruka(private val poruka: String): Fragment() {
    private lateinit var textPoruka: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_poruka, container, false)

        textPoruka = view.findViewById(R.id.tvPoruka)
        textPoruka.text = poruka

        return view
    }

    companion object {
        fun newInstance(poruka: String): FragmentPoruka = FragmentPoruka(poruka)
    }
}