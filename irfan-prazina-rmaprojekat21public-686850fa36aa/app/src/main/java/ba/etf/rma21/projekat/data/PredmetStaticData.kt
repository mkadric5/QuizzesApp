package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Predmet

fun neupisaniPredmeti(): List<Predmet> {
    return listOf(
           Predmet(10,"TP", 1),
           Predmet(11,"IM1", 1),
           Predmet(12,"MLTI",1),
           Predmet(13,"RMA",2),
           Predmet(14,"NA",2)
    )
}

fun upisaniPredmeti(): List<Predmet> {
    return listOf(
            Predmet(15,"IM2", 1),
            Predmet(16,"RPR",2)
    )
}

