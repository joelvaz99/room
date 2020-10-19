package ipvc.estg.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ipvc.estg.room.dao.CityDao
import ipvc.estg.room.entities.City
import kotlinx.coroutines.CoroutineScope

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(City::class), version = 1, exportSchema = false)
public abstract class CityDB : RoomDatabase() {

    abstract fun CityDao(): CityDao

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
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}