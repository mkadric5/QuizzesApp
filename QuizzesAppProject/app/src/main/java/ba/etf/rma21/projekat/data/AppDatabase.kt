package ba.etf.rma21.projekat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ba.etf.rma21.projekat.data.dao.*
import ba.etf.rma21.projekat.data.models.*

@Database(entities = [Account::class, Grupa::class, Predmet::class, Kviz::class,Pitanje::class,Odgovor::class,KvizTaken::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun accDao(): AccountDao
    abstract fun grupaDao(): GrupaDao
    abstract fun predmetDao(): PredmetDao
    abstract fun kvizDao(): KvizDao
    abstract fun pitanjeDao(): PitanjeDao
    abstract fun odgovorDao(): OdgovorDao
    abstract fun kvizTakenDao(): KvizTakenDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun setInstance(appdb:AppDatabase) {
            INSTANCE=appdb
        }

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "RMA21DB"
            ).build()
    }
}