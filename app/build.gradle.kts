plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version "2.0.0"
}

android {
    namespace = "com.ergophile.yd_notes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ergophile.yd_notes"
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //API Response Wrapper
    implementation("com.github.rmaprojects:apiresponsewrapper:1.7")

    //Retrofit Compose
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    //Coil
    implementation ("io.coil-kt:coil-compose:2.4.0")

    //Lifecycle Compose
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose")

    //OkHttp Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //Toasty Toast
    implementation ("com.github.GrenderG:Toasty:1.5.2")

    //Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")

    //Kotpref
    implementation("com.chibatching.kotpref:kotpref:2.5.0")
}