package com.michelle.todolist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.michelle.todolist.data.db.dao.TaskDAO
import com.michelle.todolist.data.db.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract val taskDao: TaskDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            synchronized(this) {
                var instance: AppDataBase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDataBase::class.java,
                        "app_database"
                    ).build()
                }

                return instance
            }
        }
    }
}