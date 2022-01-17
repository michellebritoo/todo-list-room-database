package com.michelle.todolist.data.db

import androidx.room.Database
import com.michelle.todolist.data.db.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDataBase {
}