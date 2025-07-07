plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "ru.tinkoff.fintech.meowle.testing"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":meowle"))

    implementation(libs.androidx.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.junit)
    implementation(libs.junit.ext.ktx)
    implementation(libs.espresso.core)
    implementation(libs.kaspresso)
    implementation(libs.kaspresso.allure)
    implementation(libs.kaspresso.compose)
    implementation(libs.allure.kotlin.model)
    implementation(libs.allure.kotlin.commons)
    implementation(libs.allure.kotlin.junit4)
    implementation(libs.allure.kotlin.android)
    implementation(libs.wiremock)
}
