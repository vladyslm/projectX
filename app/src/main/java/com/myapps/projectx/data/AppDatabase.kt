package com.myapps.projectx.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myapps.projectx.data.inbox.InboxMessage
import com.myapps.projectx.data.inbox.InboxMessageDao
import com.myapps.projectx.data.profile.User
import com.myapps.projectx.data.profile.UserDao

@Database(entities = [User::class, InboxMessage::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun inboxDao():InboxMessageDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                )
                    .createFromAsset("database/user_database")
                    .addMigrations()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}