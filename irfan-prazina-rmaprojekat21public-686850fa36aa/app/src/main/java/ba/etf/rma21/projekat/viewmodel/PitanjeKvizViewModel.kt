//package ba.etf.rma21.projekat.viewmodel
//
//import ba.etf.rma21.projekat.data.models.Kviz
//import ba.etf.rma21.projekat.data.models.Pitanje
//import ba.etf.rma21.projekat.data.models.PitanjeKviz
//import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
//
//class PitanjeKvizViewModel {
//
//    fun dajPitanjaZaKviz(kviz: Kviz): List<Pitanje> {
//        return PitanjeKvizRepository.getPitanja(kviz.naziv,kviz.nazivPredmeta)
//    }
//
//    fun dajPitanjeKvizZaPitanje(pitanje: Pitanje): PitanjeKviz {
//        return PitanjeKvizRepository.getPitanjeKvizZaPitanje(pitanje)
//    }
//
//    fun postaviOdgovor(pitanje: Pitanje, odgovor: Int) {
//        PitanjeKvizRepository.postaviOdgovor(pitanje,odgovor)
//    }
//
//    fun odgovoreno(pitanje: Pitanje): Boolean {
//        val pitanjeKviz = dajPitanjeKvizZaPitanje(pitanje)
//        return pitanjeKviz.odgovor != null
//    }
//
//    fun tacnoOdgovoreno(pitanje: Pitanje): Boolean {
//        val pitanjeKviz = dajPitanjeKvizZaPitanje(pitanje)
//        return pitanjeKviz.odgovor == pitanje.tacan
//    }
//}