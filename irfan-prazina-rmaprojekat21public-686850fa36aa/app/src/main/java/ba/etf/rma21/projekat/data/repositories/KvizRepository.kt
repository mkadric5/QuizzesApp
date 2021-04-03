package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.*
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.KvizRepository.Companion.neupisaniKvizovi

class KvizRepository {

    companion object {
        var upisaniKvizovi: MutableList<Kviz> = upisaniKvizovi().toMutableList()
        var neupisaniKvizovi: MutableList<Kviz> = neupisaniKvizovi().toMutableList()
        }

        fun getMyKvizes(): List<Kviz> {
            return emptyList()
        }

        fun getAll(): List<Kviz> {
            // TODO: Implementirati
            return emptyList()
        }

        fun getDone(): List<Kviz> {
            // TODO: Implementirati
            return emptyList()
        }

        fun getFuture(): List<Kviz> {
            // TODO: Implementirati
            return emptyList()
        }

        fun getNotTaken(): List<Kviz> {
            // TODO: Implementirati
            return emptyList()
        }
        // TODO: Implementirati i ostale potrebne metode
}