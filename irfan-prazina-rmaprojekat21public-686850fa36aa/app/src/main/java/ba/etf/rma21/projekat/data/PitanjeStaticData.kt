package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Pitanje

fun svaPitanja(): List<Pitanje> {
    return listOf(
            Pitanje("Pitanje 1","Izbaci uljeza",
            listOf("plava","zelena","crvena"),1),

            Pitanje("Pitanje 2","Izbaci uljeza",
            listOf("lav","tigar","slon"),2),

            Pitanje("Pitanje 3","Ko je otkrio Ameriku",
            listOf("Kolumbo","Magelan","Washington"),0),

            Pitanje("Pitanje 4","Glavni grad Češke",
            listOf("Beč","Prag","Rim"),1),

            Pitanje("Pitanje 5","2+2 = ",
            listOf("0","6","4"),2),

            Pitanje("Pitanje 6","Najbolji predmeti je",
            listOf("Razvoj Mobilnih aplikacija","Linearna algebra i geometrija","Operativni sistemi"),0)
    )
}