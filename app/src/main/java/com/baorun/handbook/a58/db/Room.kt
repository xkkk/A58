package com.baorun.handbook.a58.db

import androidx.room.Room
import com.baorun.handbook.a58.AppContext


private const val DB_NAME = "handbook.db"


val room =
    Room.databaseBuilder(AppContext, AppDatabase::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .build()

val dataDao = room.dataDao()
val historyDao = room.historyDao()
val collectDao = room.collectDao()