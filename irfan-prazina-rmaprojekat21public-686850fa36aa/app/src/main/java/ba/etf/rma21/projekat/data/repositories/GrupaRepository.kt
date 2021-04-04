package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.neupisaneGrupe
import ba.etf.rma21.projekat.data.upisaneGrupe

class GrupaRepository {
    companion object {
        var mojeGrupe: MutableList<Grupa> = upisaneGrupe().toMutableList()
        var ostaleGrupe: MutableList<Grupa> = neupisaneGrupe().toMutableList()
        init {
            // TODO: Implementirati
        }

        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
           val grupeZaPredmet: MutableList<Grupa> = mojeGrupe.filter{
               grupa -> grupa.nazivPredmeta == nazivPredmeta
           }.toMutableList()

            grupeZaPredmet.addAll(ostaleGrupe.filter {
                grupa -> grupa.nazivPredmeta == nazivPredmeta
            })
            return grupeZaPredmet
        }

        fun upisiUGrupu(naziv: String) {
            val g = ostaleGrupe.find{ grupa -> grupa.naziv == naziv}
            if (g != null){
                mojeGrupe.add(g)
            }
            ostaleGrupe.remove(g)
        }
    }
}