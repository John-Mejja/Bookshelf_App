package com.example.bookshelfapp.model

import be.digitalia.compose.htmlconverter.htmlToString

data class VolumesResponse(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)

data class Item(
    val id: String,
    val kind: String,
    val saleInfo: SaleInfo,
    val volumeInfo: VolumeInfo
)

data class SaleInfo(
    val buyLink: String?,
    val country: String,
    val isEbook: Boolean,
    val saleability: String
)

data class VolumeInfo(
    val allowAnonLogging: Boolean,
    val authors: List<String>?,
    val averageRating: Double,
    val categories: List<String>,
    val description: String?,
    val imageLinks: ImageLinks?,
    val industryIdentifiers: List<IndustryIdentifier>,
    val infoLink: String,
    val language: String,
    val pageCount: Int,
    val printType: String,
    val publishedDate: String?,
    val publisher: String?,
    val ratingsCount: Int,
    val subtitle: String,
    val title: String?
)

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
    val medium: String?,
    val large: String?
)

data class IndustryIdentifier(
    val identifier: String,
    val type: String
)


fun VolumesResponse.mapToBooks() : List<Book> {
    val books = mutableListOf<Book>()
    items.forEach { item ->
        val bookId = item.id
        val bookAuthor = item.volumeInfo.authors?.joinToString(",") ?: "Not Available"
        val bookTitle = item.volumeInfo.title ?: "Not Available"
        val bookThumbnailUrl = item.volumeInfo.imageLinks?.thumbnail?.replace("http","https") ?: ""
        books.add(Book(id = bookId, title = bookTitle, thumbnailUrl = bookThumbnailUrl, author = bookAuthor))
    }
    return books
}

fun Item.mapToBook() : Book {
    val id: String = id
    val title: String = volumeInfo.title ?: "Not Available"
    val thumbnailUrl: String = volumeInfo.imageLinks?.thumbnail?.replace("http","https") ?: "Unknown"
    val publishedDate: String = volumeInfo.publishedDate ?: "Unknown"
    val publisher: String = volumeInfo.publisher ?: "Unknown"
    val buyLink: String = saleInfo.buyLink ?: "Unknown"
    val author: String = volumeInfo.authors?.get(0) ?: "Unknown"
    val largeThumbnailUrl: String = volumeInfo.imageLinks?.large?.replace("http","https") ?: "Unknown"
    val favoriteType: FavoriteType = FavoriteType.NOT_FAVORITE
    val description: String = htmlToString(volumeInfo.description ?: "Not Available" )
    val authors: String = volumeInfo.authors?.joinToString(",") ?: "Not Available"
    return Book(id, title, thumbnailUrl, publishedDate, publisher, buyLink, author, largeThumbnailUrl, favoriteType, description,authors)
}
