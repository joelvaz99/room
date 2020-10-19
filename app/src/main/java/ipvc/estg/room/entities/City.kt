package ipvc.estg.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")

class City(

    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "capital") val capital: String

)
