plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("io.realm.kotlin")
}

android {
    namespace = "de.skash.timetrack"
    compileSdk = 33

    defaultConfig {
        applicationId = "de.skash.timetrack"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        viewBinding = true
    }
}

dependencies {

    // Realm
    implementation("io.realm.kotlin:library-base:1.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")


    // Hilt
    implementation("com.google.dagger:hilt-android:2.46.1")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    kapt("com.google.dagger:hilt-compiler:2.46.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // GSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Rx
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3:1.6.4")


    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}