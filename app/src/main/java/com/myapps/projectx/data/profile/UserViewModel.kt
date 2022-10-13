package com.myapps.projectx.data.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.myapps.projectx.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    val getUser: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao= AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        getUser = repository.getUser
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
}