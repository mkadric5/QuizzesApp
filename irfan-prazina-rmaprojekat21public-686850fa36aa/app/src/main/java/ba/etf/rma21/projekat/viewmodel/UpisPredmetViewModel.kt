package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class UpisPredmetViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)


    fun popuniPredmeteZaGodinu(actionPredmeti: ((predmeti: List<Predmet>) -> Unit), godina: Int) {
        scope.launch {
            actionPredmeti.invoke(dajPredmeteZaGodinu(godina))
        }
    }


    private suspend fun dajPredmeteZaGodinu(godina: Int): List<Predmet> {
        return PredmetIGrupaRepository.dajNeupisanePredmeteZaGodinu(godina)
    }

    fun popuniGrupeZaPredmet(actionGrupe: ((grupe: List<Grupa>) -> Unit), predmetId: Int) {
        scope.launch {
            if (predmetId > 0)
            actionGrupe.invoke(dajGrupeZaPredmet(predmetId))
            else actionGrupe.invoke(listOf())
        }
    }

    private suspend fun dajGrupeZaPredmet(predmetId: Int): List<Grupa> {
        return PredmetIGrupaRepository.getGrupeZaPredmet(predmetId)
    }

    fun upisiKorisnika(actionUpis: (fragmentPoruka: Fragment) -> Unit, fragmentPoruka: Fragment, grupaId: Int) {
        scope.launch {
            if (PredmetIGrupaRepository.upisiUGrupu(grupaId))
                actionUpis.invoke(fragmentPoruka)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postaviAccHash(acHash: String, context: Context) {
        scope.launch {
            AccountRepository.setContext(context)
            if (acHash == "postavi_moj")
                AccountRepository.postaviHash(AccountRepository.acHash)
            else
                AccountRepository.postaviHash(acHash)
        }
    }
}