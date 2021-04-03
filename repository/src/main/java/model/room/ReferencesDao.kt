package model.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ReferencesDao {
    @Query("SELECT * FROM ${RoomIdentifiers.referencesTableName} LIMIT 1")
    suspend fun getLastReference():ReferencesEntity
}