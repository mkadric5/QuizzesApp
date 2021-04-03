package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.neupisaniPredmeti
import ba.etf.rma21.projekat.data.upisaniPredmeti

class PredmetRepository {
    companion object {
        var upisaniPredmeti: MutableList<Predmet> = upisaniPredmeti().toMutableList()
        var neupisaniPredmeti: MutableList<Predmet> = neupisaniPredmeti().toMutableList()

        fun getUpisani(): List<Predmet> {
            return upisaniPredmeti()
        }

        fun getAll(): List<Predmet> {
            val sviPredmeti: MutableList<Predmet> = mutableListOf()
            sviPredmeti.addAll(upisaniPredmeti)
            sviPredmeti.addAll(neupisaniPredmeti)
            return sviPredmeti
        }
        // TODO: Implementirati i ostale potrebne metode
    }

}