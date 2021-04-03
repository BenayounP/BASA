package model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RoomIdentifiers.referencesTableName)
data class ReferencesEntity(@PrimaryKey(autoGenerate = true) val id: Int=0,
                            @ColumnInfo(name="query")val query: String="",
                            @ColumnInfo(name = "references") val references: Int)