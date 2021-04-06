package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

fun upisaniKvizovi(): List<Kviz> {
    val c: Calendar = Calendar.getInstance()

    c.set(2021,3,2)
    val datumPocetka = c.time

    c.set(2025,3,10)
    val datumKraja = c.time

    c.set(2021,3,3)
    val datumRada = c.time

    c.set(2021,1,3)
    val datumPocetka3 = c.time

    c.set(2021,1,6)
    val datumKraja3 = c.time

return listOf(
        //aktivan i uradjeni kviz - plava
        Kviz("IM2 kviz 1","IM2",datumPocetka,
                datumKraja,datumRada,5, "IM2 grupa 1",5F),
        //neuradjen kviz koji je prosao - crvena
        Kviz("IM2 kviz 2","IM2",datumPocetka3,
                datumKraja3,null,5, "IM2 grupa 1",null),
        //neuradjen kviz koji je aktivan - zelena
        Kviz("RPR kviz 1", "RPR", datumPocetka,
        datumKraja, null, 5,"RPR grupa 1",null)
)
}

fun neupisaniKvizovi(): List<Kviz> {
    val c: Calendar = Calendar.getInstance()

    c.set(2021,3,2)
    val datumPocetka = c.time

    c.set(2025,3,10)
    val datumKraja = c.time

    c.set(2025,5,3)
    val datumPocetka2 = c.time

    c.set(2025,5,6)
    val datumKraja2 = c.time

    c.set(2021,1,3)
    val datumPocetka3 = c.time

    c.set(2021,1,6)
    val datumKraja3 = c.time

    return listOf(
            //neuradjeni kviz - crvena
            Kviz("IM1 kviz 1","IM1",datumPocetka3,
                    datumKraja3,null,10, "IM1 grupa 1",null),
            //buduci kviz - zuta
            Kviz("IM1 kviz 2","IM1",datumPocetka2,
                    datumKraja2,null,5, "IM1 grupa 2",null),
            //trenutno aktivni kviz - zelena
            Kviz("TP kviz 1","TP",datumPocetka,
                    datumKraja,null,5, "TP grupa 1",null),
            //buduci kviz - zuta
            Kviz("TP kviz 2","TP",datumPocetka2,
                    datumKraja2,null,5, "TP grupa 2",null),
            // neuradjeni kviz - crvena
            Kviz("MLTI kviz 1","MLTI",datumPocetka3,
                    datumKraja3,null,5, "MLTI grupa 1",null),
            //trenutni kviz - zelena
            Kviz("MLTI kviz 2","MLTI",datumPocetka,
                    datumKraja,null,5, "MLTI grupa 2",null),
            //buduci kviz - zuta
            Kviz("RMA kviz 1","RMA",datumPocetka2,
                datumKraja2,null,5, "RMA grupa 1",null),
            //trenutni kviz - zelena
            Kviz("RMA kviz 2","RMA",datumPocetka,
                datumKraja,null,5, "RMA grupa 2",null),
            //trenutni kviz - zelena
            Kviz("NA kviz 2","NA",datumPocetka,
                datumKraja,null,5, "NA grupa 2",null),
            // neuradjeni kviz - crvena
            Kviz("NA kviz 1","NA",datumPocetka3,
                datumKraja3,null,5, "NA grupa 1",null)
    )
}