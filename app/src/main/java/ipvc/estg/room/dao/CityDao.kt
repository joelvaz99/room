package ipvc.estg.room.dao



import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ipvc.estg.room.entities.City

@Dao
interface CityDao {

    @Query("SELECT * from city_table ORDER BY city ASC")
    fun getAlphabetizedWords(): LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City)

    @Query("DELETE FROM city_table")
    suspend fun deleteAll()
}