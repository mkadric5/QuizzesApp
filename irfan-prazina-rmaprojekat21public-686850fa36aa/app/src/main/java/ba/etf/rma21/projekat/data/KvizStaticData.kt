package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

fun upisaniKvizovi(): List<Kviz> {
    val c: Calendar = Calendar.getInstance()

    // prvi kviz
    c.set(2021,3,2)
    val datumPocetka = c.time

    c.set(2021,3,10)
    val datumKraja = c.time

    c.set(2021,3,3)
    val datumRada = c.time

    c.set(2021,5,3)
    val datumPocetka2 = c.time

    c.set(2021,5,6)
    val datumKraja2 = c.time

    c.set(2021,1,3)
    val datumPocetka3 = c.time

    c.set(2021,1,6)
    val datumKraja3 = c.time



return listOf(
        Kviz("k1","IM2",datumPocetka, //aktivan i uradjeni kviz
                datumKraja,datumRada,5, "IM2 grupa 1",5F),
        Kviz("k1","IM1",datumPocetka, //aktivan i neuradjen kviz
               datumKraja,null,10, "IM1 grupa 1",null),
        Kviz("k1","IM1",datumPocetka2, //kviz koji ce tek biti aktivan
                datumKraja2,null,5, "IM1 grupa 2",null),
        Kviz("k1","IM2",datumPocetka3,
                datumKraja3,null,5, "IM2 grupa 2",null)
)
}

fun neupisaniKvizovi(): List<Kviz> {
    val c: Calendar = Calendar.getInstance()

    // prvi kviz
    c.set(2021,3,2)
    val datumPocetka = c.time

    c.set(2021,3,10)
    val datumKraja = c.time

    c.set(2021,5,3)
    val datumPocetka2 = c.time

    c.set(2021,5,6)
    val datumKraja2 = c.time

    c.set(2021,1,3)
    val datumPocetka3 = c.time

    c.set(2021,1,6)
    val datumKraja3 = c.time

    return listOf(
            Kviz("k1","TP",datumPocetka,
                    datumKraja,null,5, "TP grupa 1",null),
            Kviz("k1","TP",datumPocetka2,
                    datumKraja2,null,5, "TP grupa 2",null),
            Kviz("k1","MLTI",datumPocetka3,
                    datumKraja3,null,5, "MLTI grupa 1",null),
            Kviz("k1","MLTI",datumPocetka3,
                    datumKraja3,null,5, "MLTI grupa 2",null)
    )
}