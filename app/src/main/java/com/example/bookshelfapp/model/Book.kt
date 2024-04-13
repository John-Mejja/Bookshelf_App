package com.example.bookshelfapp.model

data class Book(
    val id: String = "",
    val title: String = "",
    val thumbnailUrl: String = "",
    val publishedDate: String = "",
    val publisher: String = "",
    val buyLink: String = "",
    val author: String = "",
    val largeThumbnailUrl: String = "",
    var favoriteType: FavoriteType = FavoriteType.NOT_FAVORITE,
    val description: String = "",
    val authors: String = ""
)

enum class FavoriteType{
    FAVORITE,
    NOT_FAVORITE
}

fun List<Book>.extractBooksIds(): List<String> {
    val booksIds = mutableListOf<String>()
    this.forEach { book ->
        booksIds.add(book.id)
    }
    return booksIds
}


