package com.myapps.projectx.data.inbox

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InboxMessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMessage(message: InboxMessage)

    @Query("SELECT * FROM inbox_table ORDER BY id DESC")
    fun readAllMessages() : LiveData<List<InboxMessage>>
}