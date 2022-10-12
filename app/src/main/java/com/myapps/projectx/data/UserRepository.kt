package com.myapps.projectx.data

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val getUser: LiveData<List<User>> = userDao.getUser()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
}