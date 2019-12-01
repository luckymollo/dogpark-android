package com.luckymollo.dogpark.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luckymollo.dogpark.core.session.Session
import com.luckymollo.dogpark.core.session.SessionDao

@Database(entities = [Session::class], version = 1)
@TypeConverters(RoomTypeConverters::class)
abstract class CoreDatabaseManager : RoomDatabase() {

    abstract val sessionDao: SessionDao

    companion object {

        fun buildDatabase(context: Context): CoreDatabaseManager {
            return Room.databaseBuilder(context, CoreDatabaseManager::class.java, "core-db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
