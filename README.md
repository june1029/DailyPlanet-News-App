# DailyPlanet News App 🚀

DailyPlanet is a modern, feature-rich news application for Android, built entirely with **Jetpack Compose** and structured around the **MVVM** architecture. It delivers live top headlines from around the world using the NewsAPI.org service and provides a seamless, dynamic, and user-friendly experience for browsing, searching, and saving news.

This project was built to demonstrate a mastery of modern Android development practices and libraries.

## 📸 Screenshots

| Home Screen | Search Screen | Favorites Screen | About Dialog |
| :---: | :---: | :---: | :---: |
| *(Your Screenshot Here)* | *(Your Screenshot Here)* | *(Your Screenshot Here)* | *(Your Screenshot Here)* |

*You can add your own screenshots here by editing this README. [How to add images to a README](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax#images)*

## ✨ Key Features

* **📰 Live News Feed:** Fetches the latest top headlines from NewsAPI.org.
* **👈 Swipeable Category Tabs:** Browse news across multiple categories (`General`, `Business`, `Technology`, `Sports`, etc.) with a smooth, swipeable tab interface.
* **♾️ Infinite Scroll (Pagination):** Automatically loads more articles as the user scrolls to the bottom of the list.
* **🔍 Full-Text Search:** A dedicated screen allows users to find articles on any topic.
* **❤️ Favorites System:** Users can save articles for later by tapping a heart icon. Favorites are stored locally on the device using a **Room** database and are available offline.
* **🌐 In-App Browser:** Articles open seamlessly within the app using Chrome Custom Tabs for a fast and integrated reading experience.
* **🎨 Dynamic UI:**
    * **Shimmer Loading Effect:** A professional, animated placeholder is shown while articles are being fetched for the first time.
    * **Modern Theme:** A beautiful, minimalistic dark theme with a consistent and visually appealing color scheme.
    * **Graceful Image Loading:** Displays a placeholder for articles that are missing an image.
* **📤 Share Articles:** Easily share an article's URL with other apps using the native Android share sheet.

## 🛠️ Technologies & Architecture

This project is a showcase of modern Android development.

* **UI:** 100% **Jetpack Compose**.
* **Architecture:** **MVVM** (Model-View-ViewModel).
* **Asynchronous Programming:** **Kotlin Coroutines** and **Flow** for managing background tasks and reactive state.
* **Networking:** **Retrofit** for making type-safe requests to the REST API, with **OkHttp** and `HttpLoggingInterceptor` for debugging.
* **Local Database:** **Room** for persisting user's favorite articles.
* **Navigation:** **Jetpack Navigation for Compose** to handle navigation between screens.
* **Image Loading:** **Coil** for loading and caching images efficiently.
* **Animations:** Compose Shimmer for loading effects and `animateItemPlacement` for list animations.
* **Dependency Management:** Gradle with `libs.versions.toml` (Version Catalog).

## ⚙️ Setup & Installation

To build and run this project yourself, you will need to get a free API key from [NewsAPI.org](https://newsapi.org/).

1.  Clone the repository:
    ```bash
    git clone [https://github.com/Hamilwt/DailyPlanet-News-App.git](https://github.com/Hamilwt/DailyPlanet-News-App.git)
    ```
2.  Open the project in Android Studio.
3.  Navigate to the file `app/src/main/java/com/example/dailyplanet/api/Constants.kt`.
4.  Paste your own API key into the `NEWS_API_KEY` variable:
    ```kotlin
    const val NEWS_API_KEY = "PASTE_YOUR_NEWSAPI_KEY_HERE"
    ```
5.  Build and run the app.

## 📜 License
