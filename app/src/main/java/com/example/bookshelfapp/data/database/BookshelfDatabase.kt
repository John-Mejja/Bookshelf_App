package com.example.bookshelfapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteBook::class], version = 2, exportSchema = false)
abstract class BookshelfDatabase: RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
    companion object{
        @Volatile
        private var Instance: BookshelfDatabase? = null
        fun getDatabase(context: Context) : BookshelfDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BookshelfDatabase::class.java, "bookshelf_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}