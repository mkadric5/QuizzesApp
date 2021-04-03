package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Grupa

fun upisaneGrupe(): List<Grupa> {
    return listOf(
            Grupa("IM2 grupa 1","IM2")
    )
}

fun neupisaneGrupe(): List<Grupa> {
    return listOf(
            Grupa("IM1 grupa 1","IM1"),
            Grupa("IM1 grupa 2","IM1"),
            Grupa("TP grupa 1","TP"),
            Grupa("TP grupa 2","TP"),
            Grupa("MLTI grupa 1","MLTI"),
            Grupa("MLTI grupa 2","MLTI"),
            Grupa("IM2 grupa 2","IM2")
    )
}