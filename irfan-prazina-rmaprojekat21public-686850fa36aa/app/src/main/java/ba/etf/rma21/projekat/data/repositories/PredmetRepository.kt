package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.neupisaniPredmeti
import ba.etf.rma21.projekat.data.upisaniPredmeti

class PredmetRepository {
    companion object {
        var mojiPredmeti: MutableList<Predmet> = upisaniPredmeti().toMutableList()
        var ostaliPredmeti: MutableList<Predmet> = neupisaniPredmeti().toMutableList()

        fun getUpisani(): List<Predmet> {
            return upisaniPredmeti()
        }

        fun getAll(): List<Predmet> {
            val sviPredmeti: MutableList<Predmet> = mutableListOf()
            sviPredmeti.addAll(mojiPredmeti)
            sviPredmeti.addAll(ostaliPredmeti)
            return sviPredmeti
        }

        fun upisiNaPredmet(naziv: String) {
            val p = ostaliPredmeti.find { predmet -> predmet.naziv == naziv }
            if (p != null) {
                mojiPredmeti.add(p)
            }
            ostaliPredmeti.remove(p)
        }
    }

}