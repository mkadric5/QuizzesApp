package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.neupisaneGrupe
import ba.etf.rma21.projekat.data.upisaneGrupe

class GrupaRepository {
    companion object {
        var upisaneGrupe: MutableList<Grupa> = upisaneGrupe().toMutableList()
        var neupisaneGrupe: MutableList<Grupa> = neupisaneGrupe().toMutableList()
        init {
            // TODO: Implementirati
        }

        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
           val grupeZaPredmet: MutableList<Grupa> = upisaneGrupe.filter{
               grupa -> grupa.nazivPredmeta == nazivPredmeta
           }.toMutableList()

            grupeZaPredmet.addAll(neupisaneGrupe.filter {
                grupa -> grupa.nazivPredmeta == nazivPredmeta
            })
            return grupeZaPredmet
        }
    }
}