package com.example.bookshelfapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.model.FavoriteType

@Entity(tableName = "favorite")
data class FavoriteBook(
    @PrimaryKey
    @ColumnInfo(name = "book_id")
    val bookId: String,
    @ColumnInfo(name = "book_title")
    val bookTitle: String,
    @ColumnInfo(name = "book_thumbnail")
    val bookThumbnail: String,
    @ColumnInfo(name = "book_author")
    val bookAuthors: String,
    @ColumnInfo(name = "favorite_type")
    val favoriteType: FavoriteType
)

fun Book.mapToFavoriteBook() : FavoriteBook {
    val bookId = id
    val bookTitle = title
    val bookAuthors = authors
    val bookThumbnail = thumbnailUrl

    return FavoriteBook(
        bookId = bookId,
        bookTitle = bookTitle,
        bookThumbnail = bookThumbnail,
        bookAuthors = bookAuthors,
        favoriteType = FavoriteType.FAVORITE
    )
}
fun FavoriteBook.mapToBook() : Book {
    val id = bookId
    val title = bookTitle
    val thumbnailUrl = bookThumbnail
    val authors = bookAuthors
    return Book(
        id = id,
        title = title,
        thumbnailUrl =  thumbnailUrl,
        authors = authors,
        favoriteType = FavoriteType.FAVORITE
    )
}
fun List<FavoriteBook>.mapToBookList(): List<Book> {
    val books = mutableListOf<Book>()
    this.forEach { favoriteBook ->
        books.add(favoriteBook.mapToBook())
    }
    return books
}