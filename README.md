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
* Architecture: MVVM design principles are used so as to have a structured and maintainable approach to the UI development through separation of concerns for ease of maintainability and testability of code. The app
    * The app is mainly divided into the following layers:
      1. UI - includes screens/composables, standard components/designs which can be used through out the app and state holders (that is, ViewModels).
      2. Data - includes local and remote data sources, and repository together with its implementation for the app.
      3. DI - includes all data injection files are hosted here.
      4. Utils - not standard or mandatory to have but good to have so as to host various files which may have reusable methods or functions.
* Room: SQLite database for Android to store data locally.
* Dependency Injection(DI): Koin - a light weight DI for android development.
* Coroutines: Flow for asynchronous data streaming.
* Retrofit: A type-safe HTTP client (networking) for Android and Java.
* Moshi: For parsing the JSON format.
* Maps: Google play location library for getting the current location of device.
* Version Control: Git and Github.


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

# Static Code Analysis:
This Weather App uses spotlessApply to ensure there is consistent code formatting throughout the code. It uses checks defined via .editorconfig file. SpotlessApply is automatically trigger before committing (pre-commit) code to remote repository. Always a good practise at minimal to run spotlessCheck, spotlessApply and ktlintCheck.

To run the static checks for the app use: (Within the terminal)
1. ./gradlew spotlessApply - to apply formatting and fix violations.
2. ./gradlew spotlessCheck - to check violations without applying formatting.


# Permissions:
* Access to Internet - needed for connection to the API so as to fetch the weather data. If user is not connected to the Internet the app will prompt the user to do so.
* Access to Location - needed so as to get device's current location(latitude and longitude) used to fetch the current location weather condition and the 5-day forecast. The app will prompt the user to grant permission to user location.


# Pre-requisities & API Documentation:
* Google Maps API key - ensure as a Developer you have a valid google maps api key so as to be able to use maps; Documentation: https://developers.google.com/maps/documentation/android-sdk/get-api-key
* Weather API key - ensure as a Developer you have a valid weather api key so as to be able to fetch weather information needed; Documentation: Current weather data - https://openweathermap.org/current and 5 day weather forecast - https://openweathermap.org/forecast5


# How it's build:
- After setting up your API keys needed for the project, import/add them within the app and reference them accordingly. That is, Google Map API should be set within the Manifest file within application tag. Also add the dependecies needed as shown above under dependencies. Then also import the Open Weather API and reference it to when calling the API keys. See Constants.kt file.


# NOTE:
* During initial launch, the app sometimes does not pick your current location coordinates(latlng), kindly ensure location is on and relaunch incase it does not pick your coordinates automatically.
* No major bugs yet observed in the app but there is still a few tweaks which can be done since the app is still under development. Incase of any bugs, any feedback will be highly appreciated.


# TO-DO:
1. Tests - write test cases for the app.
2. Ktlint check - finalize on ktlint check before come push.


# Screenshots:

<table style="border-spacing: 10px;">
<tr>
	<td>
		<img src="./screenshots/screenshot_0.jpeg"/>
	</td>
	<td>
		<img src="./screenshots/screenshot_1.jpeg"/>
	</td>
</tr>

<tr>
	<td>
		<img src="./screenshots/screenshot_2.jpeg"/>
	</td>
	<td>
		<img src="./screenshots/screenshot_3.jpeg"/>
	</td>
</tr>

<tr>
	<td>
		<img src="./screenshots/screenshot_4.jpeg"/>
	</td>
	<td>
		<img src="./screenshots/screenshot_5.jpeg"/>
	</td>
</tr>

<tr>
	<td>
		<img src="./screenshots/screenshot_6.jpeg"/>
	</td>
	<td>
		<img src="./screenshots/screenshot_7.jpeg"/>
	</td>
</tr>

</table>

