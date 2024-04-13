package com.example.bookshelfapp.data.repository

import com.example.bookshelfapp.data.network.GoogleApisBookService
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.mapToBook
import com.example.bookshelfapp.model.mapToBooks

interface NetworkBookshelfRepository {
    suspend fun getBooks( query: String): List<Book>?
    suspend fun getBook(id: String): Book?
}

class NetworkServiceBookshelfRepository (
    private val googleApisBookService: GoogleApisBookService
) : NetworkBookshelfRepository {
    override suspend fun getBooks(query: String): List<Book>? {
        return try {
            val response = googleApisBookService.searchBooks(query)
            if (response.isSuccessful) {
                response.body()?.mapToBooks() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
               null
        }
    }
    override suspend fun getBook(id: String): Book? {
        return try {
            val response = googleApisBookService.getBook(id)
            if (response.isSuccessful) {
                response.body()?.mapToBook()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}