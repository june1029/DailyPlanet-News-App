<img width="1408" height="2974" alt="Screenshot_20250922_152330" src="https://github.com/user-attachments/assets/9e58ff71-85b9-4687-bf93-70ef8c992167" /># DailyPlanet News App 🚀

DailyPlanet is a modern, feature-rich news application for Android, built entirely with **Jetpack Compose** and structured around the **MVVM** architecture. It delivers live top headlines from around the world using the NewsAPI.org service and provides a seamless, dynamic, and user-friendly experience for browsing, searching, and saving news.

This project was built to demonstrate a mastery of modern Android development practices and libraries.

## 📸 Screenshots

| Home Screen | Search Screen | Favorites Screen | About Dialog |
| :---: | :---: | :---: | :---: |
| <img width="1408" height="2974" alt="Screenshot_20250922_151943" src="https://github.com/user-attachments/assets/d0edaae6-ef20-487a-9a34-8b5952900680" /> | <img width="1408" height="2974" alt="Screenshot_20250922_152330" src="https://github.com/user-attachments/assets/0835465c-9be1-40f4-8e16-c283b3632cb2" /> | <img width="1408" height="2974" alt="Screenshot_20250922_152349" src="https://github.com/user-attachments/assets/9d11a475-3c7c-4a33-ac16-3dde61347ef3" /> | <img width="1408" height="2974" alt="Screenshot_20250922_152412" src="https://github.com/user-attachments/assets/bae1eab6-739a-4d6a-8671-1e6b0221b5c2" /> |

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
    git clone https://github.com/Hamilwt/DailyPlanet-News-App.git
    ```
2.  Open the project in Android Studio.
3.  Navigate to the file `app/src/main/java/com/example/dailyplanet/api/Constants.kt`.
4.  Paste your own API key into the `NEWS_API_KEY` variable:
    ```kotlin
    const val NEWS_API_KEY = "PASTE_YOUR_NEWSAPI_KEY_HERE"
    ```
5.  Build and run the app.

## 🙏 Acknowledgements

* This project is powered by the [NewsAPI.org](https://newsapi.org) service.
* Built with the incredible open-source libraries from the Android Jetpack and Kotlin ecosystems.
