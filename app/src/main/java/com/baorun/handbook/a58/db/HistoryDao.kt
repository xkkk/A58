package com.baorun.handbook.a58.db

import androidx.room.*
import com.baorun.handbook.a58.data.ChildrenData

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: HistoryEntity)

    @Delete
    suspend fun deleteData(data: HistoryEntity)

    @Transaction
    @Query("SELECT id,parentId,belong,htmlUrl,description,name FROM table_history")
    suspend fun query():List<ChildrenData>
}