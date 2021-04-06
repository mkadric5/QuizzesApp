package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Predmet

fun neupisaniPredmeti(): List<Predmet> {
    return listOf(
           Predmet("TP", 1),
           Predmet("IM1", 1),
           Predmet("MLTI",1),
           Predmet("RMA",2),
           Predmet("NA",2)
    )
}

fun upisaniPredmeti(): List<Predmet> {
    return listOf(
            Predmet("IM2", 1),
            Predmet("RPR",2)
    )
}

