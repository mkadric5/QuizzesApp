package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

fun upisaniKvizovi(): List<Kviz> {
return listOf(
        Kviz("k1","IM2",Date(2021,4,10),
                Date(2021,4,11),Date(),5,
                "IM2 grupa 1",0F),
        Kviz("k1","IM1",Date(2021,4,2),
                Date(2021,4,5),Date(),5,
                "IM1 grupa 1",5.2F)
)
}

fun neupisaniKvizovi(): List<Kviz> {
    return listOf(
            Kviz("k1","IM1",Date(2021,4,2),
                    Date(2021,4,2),Date(2021,4,2),5,
                    "IM1 grupa 2",5.2F),
            Kviz("k1","IM2",Date(2021,4,2),
                    Date(2021,4,2),Date(2021,4,2),5,
                    "IM2 grupa 2",5.2F),

            Kviz("k1","TP",Date(2021,4,2),
                    Date(2021,4,2),Date(2021,4,2),5,
                    "TP grupa 1",5.2F),
            Kviz("k1","TP",Date(2021,4,2),
                    Date(2021,4,2),Date(2021,4,2),5,
                    "TP grupa 2",5.2F),

            Kviz("k1","MLTI",Date(2021,4,2),
                    Date(2021,4,2),Date(2021,4,2),5,
                    "MLTI grupa 1",5.2F),
            Kviz("k1","MLTI",Date(2021,4,2),
                    Date(2021,4,2),Date(2021,4,2),5,
                    "MLTI grupa 2",5.2F)
    )
}