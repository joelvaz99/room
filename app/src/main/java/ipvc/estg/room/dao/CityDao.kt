package ipvc.estg.room.dao



import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.room.entities.City

@Dao
interface CityDao {

   
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City)

    @Query("DELETE FROM city_table")
    suspend fun deleteAll()

    //
    @Query("SELECT * from city_table ORDER BY city ASC")
    fun getAllCities(): LiveData<List<City>>

    @Query("SELECT * from city_table WHERE capital == :capital")
    fun getCitiesByCountry(capital: String): LiveData<List<City>>

    @Query("SELECT * from city_table WHERE city == :city")
    fun getCountryFromCity(city: String): LiveData<City>

    @Update
    suspend fun updateCity(city:City)

    @Query("DELETE FROM city_table WHERE city=:city ")
    suspend fun deleteByCity(city:String)

    @Query("UPDATE city_table SET capital=:capital WHERE city == :city")
    fun updateCountryFromCity(city:String, capital: String)





}