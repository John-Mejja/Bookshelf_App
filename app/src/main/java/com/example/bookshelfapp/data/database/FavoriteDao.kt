package com.example.bookshelfapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteBook: FavoriteBook)

    @Delete
    suspend fun delete(favoriteBook: FavoriteBook)

    @Query("SELECT * FROM favorite ORDER BY book_title")
    fun getFavoriteBooks() : Flow<List<FavoriteBook>>
}