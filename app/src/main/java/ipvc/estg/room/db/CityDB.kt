package ipvc.estg.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ipvc.estg.room.dao.CityDao
import ipvc.estg.room.entities.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(City::class), version = 5, exportSchema = false)
public abstract class CityDB : RoomDatabase() {

    abstract fun CityDao(): CityDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                   var cityDao=database.CityDao()

                    // Delete all content here.
                    cityDao.deleteAll()

                    // Colocar duas cidades de Inicio
                   var city = City(1, city ="Viana do Castelo", capital ="Portugal" )
                    cityDao.insert(city)

                    city = City(2, city ="Porto", capital ="Portugal" )
                    cityDao.insert(city)

                    city = City(3, city ="Aveiro", capital ="Portugal" )
                    cityDao.insert(city)
                }
            }
        }
    }
/*
        suspend fun populateDatabase(CityDao: CityDao) {
            // Delete all content here.
            CityDao.deleteAll()

            // Add sample words.
            var city = City( id =  1, city = "Viana Do Castelo", capital = "Portugal")
            CityDao.insert(city)

            city = City(id = 2, city = "Porto", capital = "Portugal")
            CityDao.insert(city)

        }
    }
*/

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CityDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CityDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityDB::class.java,
                        "cities_database"
                )

                    //estrategia destruicao
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}