package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Predmet

fun neupisaniPredmeti(): List<Predmet> {
    return listOf(
           Predmet("IM1", 1),
           Predmet("TP", 1),
           Predmet("MLTI",1)
    )
}

fun upisaniPredmeti(): List<Predmet> {
    return listOf(
            Predmet("IM2", 1)
    )
}

