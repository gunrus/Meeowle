plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "ru.tinkoff.fintech.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.tinkoff.fintech.meowle"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "io.qameta.allure.android.runners.AllureAndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    dataBinding {
        enable = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kapt {
        correctErrorTypes = true
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(project(":meowle"))
    implementation(libs.kotlinx.serialization.json)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.interceptor)

    // Hilt
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.compiler.android)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Test
    androidTestImplementation(project(":meowle-testing"))
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext.ktx)
    androidTestImplementation(libs.uiautomator)
    androidTestImplementation(libs.compose.ui.test.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.kaspresso)
    androidTestImplementation(libs.kaspresso.allure)
    androidTestImplementation(libs.kaspresso.compose)
    androidTestImplementation(libs.allure.kotlin.model)
    androidTestImplementation(libs.allure.kotlin.commons)
    androidTestImplementation(libs.allure.kotlin.junit4)
    androidTestImplementation(libs.allure.kotlin.android)
    androidTestImplementation(libs.wiremock)
    androidTestImplementation(libs.compose.testing.junit4)
    debugImplementation(libs.compose.testing.manifest)
}
