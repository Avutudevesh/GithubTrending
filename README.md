# GitHub Trending Repositories App
An Android App which fetches currently trending github repositories from a public api and displays it to the users.

# Features!
    - Shimmer animation for loading state
    - Caching of data
    - Items expand on clicking item in list to provide additional details
    - Sort by name of repository or number of starts of each repository

### Tech
This app is built using kotlin programming language and is built using MVVM architecture.
Uses below libraries to perform certain tasks.
    * Dagger2 - For dependency injection
    * Retrofit - For Networking and caching network data
    * Espresso - For UI Tests
    * Junit and Roboelectric - For Unit Tests
    * Glide - To fetch images from network
    * Coroutines - To perform async tasks

### Installation
* Building the app
./gradlew clean assembleDebug
* Installing the app on emulator or device
./gradlew clean installDebug
* Running unit tests
./gradlew clean test
* Running Espresso tests
./gradlew clean connectedAndroidTest


