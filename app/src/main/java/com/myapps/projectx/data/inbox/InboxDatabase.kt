package com.myapps.projectx.data.inbox

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [InboxMessage::class], version = 1, exportSchema = false)
abstract class InboxDatabase:RoomDatabase() {

    abstract fun inboxMessageDao(): InboxMessageDao

    companion object{
        @Volatile
        private var INSTANCE: InboxDatabase? =null

        fun getDatabase(context: Context) :InboxDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InboxDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}