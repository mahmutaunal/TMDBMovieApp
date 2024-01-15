# MovieDB App

This application is a simple Kotlin app developed using The Movie Database (TMDb) API. The app includes a main page where movies are listed, search and filtering options, and the ability to navigate to a detail page for each movie.

## Technical Details

This project incorporates the following technologies:

- **MVVM (Model-View-ViewModel):** The app is designed using the MVVM architecture. ViewModel is used to manage data flow and establish a connection between the UI and business logic.

- **Coroutines:** Kotlin coroutines are used to make asynchronous programming more efficient and straightforward.

- **LiveData:** LiveData is utilized for data observation and to facilitate UI updates.

- **ViewBinding:** ViewBinding is used to access XML-based UI elements.

- **Retrofit:** Retrofit library is employed for HTTP requests.

- **Glide:** Glide library is used for loading and displaying images.

- **Navigation Component:** Navigation Component is used to manage navigation between screens.

- **Fragment:** Fragment is used to represent UI components.

## Usage

To run the project, follow these steps:

1. Clone this repository to your computer.
2. Open Android Studio.
3. Select File -> New -> Open Project, and open the cloned repository as a project.
4. Obtain your API key from [The Movie Database](https://www.themoviedb.org/documentation/api).
5. Add the API key to the `local.properties` file: `TMDB_API_KEY="your_api_key"`

## Screenshots

[<img src="/screenshots/Screenshot_20240114_201847.png" align="center"
width="200"
    hspace="10" vspace="10">](/screenshots/Screenshot_20240114_201847.png)
[<img src="/screenshots/Screenshot_20240114_201903.png" align="left"
width="200"
    hspace="10" vspace="10">](/screenshots/Screenshot_20240114_201903.png)

## License

This application is released under MIT license (see [LICENSE](LICENSE)).
