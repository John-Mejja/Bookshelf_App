# Bookshelf App

Bookshelf is an android from Android Basics with Compose project, that allows users to search for books. The app fetches book volume from Google Books Apis Service. The user can also save their favorite books for future reference.

## Features

* Search for books using the Google Books APIs
* Browse books from the given categories 
* View book details, including the book cover image, author and description
* Add book to Favorite for quick access later

### Future Features (Not Implemented yet)
| Feature | Description | Status |
| --- | --- | --- |
| Authentication | Add users authentication to login the app | Not yet started |
| Social Sharing | Allow users to share books to other social account | Not yet started |
| Better User Interface | Improve the user interface for better user experience | Not yet started |

## Technologies Used
* **Google Books APIs :** The app fetches books volumes  from Google Books APIs.
* **Retrofit :** The app uses REST APIs using Retrofit, a networking library, to make API request and receive response from external APIs.
* **GSON Converter :** The app uses GSON converter to convert JSON data response from the API into kotlin objects.
* **Dependency Injection :** The app uses manual dependency injection to provide repository to the viewModel.
* **Kotlin Coroutine :** The app uses coroutine to asynchronously execute soem task in the background, improving UI responsiveness.
* **Coil :** The app uses coil, a library that downloads, buffers, decodes and caches images, to display images to the app.
* **Room :** The app uses Room database, to store favorite books added by the user.
* **Kotlin Flows :** The app uses Flow, a kotlin language feature that searves as a reactive programming framework, to update Favorite Books from the database.
* **Jetpack Compose :** The app uses Jetpack compose, a modern toolkit for building Android UIs, to create the app's UI.
  
## Screenshots
![Bookshelf App homepage that displays favorite books and recommended books.](/screenshots/home.jpg)
![Bookshelf App query screen that dispalys users search results.](/screenshots/query.jpg)
![Bookshelf App favorite screen that displays all the users favorite books in a grid list.](/screenshots/favorite.jpg)
![Bookshelf App details screen that displays the book details including description, author, published date and publisher. ](/screenshots/details.jpg)
## Installation
To install the app, clone the repository and open the project in Android Studio. The app can be run on an emulator or a physical device.

## Acknowledgements
* [Google Books API](https://developers.google.com/books/docs/v1/using#PerformingSearch)
* [Android Basics With Compose](https://developer.android.com/courses/pathways/android-basics-compose-unit-5-pathway-2)
