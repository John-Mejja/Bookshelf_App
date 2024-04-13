package com.example.bookshelfapp.data.repository

import com.example.bookshelfapp.data.database.FavoriteBook
import com.example.bookshelfapp.data.database.FavoriteDao
import kotlinx.coroutines.flow.Flow

interface OfflineBookshelfRepository {
    suspend fun addFavoriteBook(favoriteBook: FavoriteBook)
    suspend fun removeFavoriteBook(favoriteBook: FavoriteBook)
    fun getFavoriteBooksStream(): Flow<List<FavoriteBook>>
}

class OfflineServiceRepository(
    private val favoriteDao: FavoriteDao
) : OfflineBookshelfRepository {
    override suspend fun addFavoriteBook(favoriteBook: FavoriteBook) = favoriteDao.insert(favoriteBook)

    override suspend fun removeFavoriteBook(favoriteBook: FavoriteBook) = favoriteDao.delete(favoriteBook)

    override fun getFavoriteBooksStream(): Flow<List<FavoriteBook>> = favoriteDao.getFavoriteBooks()
}