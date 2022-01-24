package com.michelle.todolist.di

import androidx.room.Room
import com.michelle.todolist.data.db.AppDataBase
import com.michelle.todolist.data.repository.DataBaseDataSource
import com.michelle.todolist.data.repository.TaskRepository
import com.michelle.todolist.ui.task.TaskViewModel
import com.michelle.todolist.ui.taskList.TaskListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taskModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDataBase::class.java,
            "app_database"
        ).build()
    }

    single { get<AppDataBase>().taskDao }

    single<TaskRepository> {
        DataBaseDataSource(get())
    }


    viewModel { TaskViewModel(repository = get()) }
    viewModel { TaskListViewModel(repository = get()) }
}
