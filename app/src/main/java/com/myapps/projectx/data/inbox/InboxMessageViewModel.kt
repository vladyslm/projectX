package com.myapps.projectx.data.inbox

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.myapps.projectx.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InboxMessageViewModel(application: Application): AndroidViewModel(application) {

    val readAllMessages: LiveData<List<InboxMessage>>
    private val repository: InboxMessageRepository

    init {
        val inboxMessageDao = AppDatabase.getDatabase(application).inboxDao()
        repository = InboxMessageRepository(inboxMessageDao)
        readAllMessages = repository.readAll
    }

    fun addInboxMessage(inboxMessage: InboxMessage){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addInboxMessage(inboxMessage)
        }
    }

}