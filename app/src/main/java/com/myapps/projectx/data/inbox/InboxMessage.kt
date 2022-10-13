package com.myapps.projectx.data.inbox

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inbox_table")
data class InboxMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val message: String,
    val from: String,

    // TODO: change to the timestamp
    val date: String,
    val isRead: Boolean
)