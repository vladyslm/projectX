package com.myapps.projectx.data.inbox

import androidx.lifecycle.LiveData

class InboxMessageRepository(private val inboxMessageDao: InboxMessageDao) {

    val readAll: LiveData<List<InboxMessage>> = inboxMessageDao.readAllMessages()

    suspend fun addInboxMessage(inboxMessage: InboxMessage){
        inboxMessageDao.addMessage(inboxMessage)
    }

    suspend fun markAsRead(messageId: Int){
        inboxMessageDao.markAsRead(messageId)
    }
}