plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.dvt.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dvt.weatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(libs.accompanist.drawablepainter)

    // Koin for DI
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Network
    implementation(libs.logging.interceptor)

    // Retrofit for network requests
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.converter.moshi)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    // Room database
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // Moshi for parsing the JSON format
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // Logging
    implementation(libs.jakewharton.timber)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Location
    implementation(libs.play.services.auth)
    implementation(libs.play.services.location)

    // Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.compose)

    // Google maps
    implementation(libs.android.maps.utils)
    implementation(libs.maps.utils.ktx)
    implementation(libs.play.services.maps)
    implementation(libs.android.maps.ktx)
    implementation(libs.maps.compose)

    implementation(libs.google.accompanist.permissions)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
