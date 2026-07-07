plugins {
    alias(libs.plugins.androidApplication)
    // Kotlin Android (nicht kotlinMultiplatform) — das App-Modul ist kein KMP-Modul,
    // es konsumiert nur das :shared-Framework
    alias(libs.plugins.kotlinAndroid)
    // Kotlin 2.0: Compose Compiler wird als separates Plugin eingebunden,
    // ersetzt das veraltete composeOptions.kotlinCompilerExtensionVersion
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.workshop.kmp.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.workshop.kmp.android"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
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
    implementation(projects.shared)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.navigation.compose)
}
