# WEATHER APP
This is a weather forecast app which provides users with timely and current weather information.

# Features:
* Current location weather.
* 5-day weather forecast with a detailed summary.
* Option to set favourite(s) current location weather.
* Option to view favourite locations on a map with various map types.


# Tech-Stack:
* Software IDE: Android Studio.
* Language: Kotlin - a statically typed programming language used for development of this android app.
* UI: Jetpack Compose - a modern UI toolkit for building android applications.
* Navigation: Jetpack Navigation - a library for building navigation on android.
* Architecture: MVVM design principles so as to have a structured and maitainable approach to the UI development through separation of concerns for ease of maintainability and testability of code.
* Room: SQLite database for Android to store data locally.
* Dependency Injection(DI): Koin - a light weight DI for android development.
* Coroutines: Flow for asynchronous data streaming.
* Retrofit: A type-safe HTTP client (networking) for Android and Java.
* Moshi: For parsing the JSON format.
* Maps: Google play location library for getting the current location of device.
* Version: Git and Github.
* Plugins: 


# Dependencies:
	* DI: ('io.insert-koin:koin-android:{version}'), (io.insert-koin:koin-androidx-compose:{version}'') - to handle DI and avoid need for manual DI in the project.
	* Network: ('com.squareup.okhttp3:logging-interceptor:{version}') - interceptor for OkHttp logging.
	* Retrofit Coroutines Adapter: ('com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:{version}') - enables use of coroutines with Retrofit, allowing for asynchronous processing of data.
	* Retrofit Moshi Converter: ('com.squareup.retrofit2:converter-moshi:{moshi}') - HTTP client library makes it easier to consume RESTful APIs and displaying the results.
	* Timber: ('com.jakewharton.timber:timber:{version}') - logging library for Android.
	* Google Permissions: ('com.google.accompanist:accompanist-permissions:{version}') - library for handling Android permissions with Compose.
	* Google Maps: ('com.google.maps.android:maps-compose:{version}'), ('com.google.android.gms:play-services-maps:{version}') - to handle google maps within the app.
	* Room: ('androidx.room:room-ktx:{version}') - a library to enable persistent local database setup and usage.
	* Viewmodel: ('androidx.lifecycle:lifecycle-viewmodel-compose:{version}') - to store and manage UI-related data in a lifecycle conscious way.
	* Lifecycle: ('androidx.lifecycle:lifecycle-runtime-ktx:{version}') - kotlin extensions to perform action when lifecycle states changes.

		
* The dependencies' references can be found within libs.version.toml file.	


# Permissions:
* Access to Internet - needed for connection to the API so as to fetch the weather data. If user is not connected to the Internet the app will prompt the user to do so.
* Access to Location - needed so as to get device's current location(latitude and longitude) used to fetch the current location weather condition and the 5-day forecast. The app will prompt the user to grant permission to user location.



# Pre-requisities & API Documentation:
* Google Maps API key - ensure as a Developer you have a valid google maps api key so as to be able to use maps; Documentation: https://developers.google.com/maps/documentation/android-sdk/get-api-key
* Weather API key - ensure as a Developer you have a valid weather api key so as to be able to fetch weather information needed; Documentation: Current weather data - https://openweathermap.org/current and 5 day weather forecast - https://openweathermap.org/forecast5


# How it's build
- After setting up your API keys needed for the project, import/add them within the app and reference them accordingly. That is, Google Map API should be set within the Manifest file within application tag. Also add the dependecies needed as shown above under dependencies. Then also import the Open Weather API and reference it to when calling the API keys. See Constants.kt file.


# More info:
* No online tool used to generate models.
* No bugs yet observed in the app but there is still a few tweaks which can be done. Incase of any bugs, any feedback will be highly appreciated.


# TO-DO:
* 1. Spotless initial setup has been done but encountered a few bugs. To be worked on.
* 2. Tests - also write test cases for the app.


# Screenshots:

<table>
<tr>
<td>
<img width="800" height="400" src="./screenshots/homepage_1.jpeg"/>
</td>
</tr>
<td>
<img width="800" height="400" src="./screenshots/homepage_2.jpeg"/>
</td>
</tr>
</table>

