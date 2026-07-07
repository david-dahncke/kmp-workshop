pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

// Aktiviert projects.shared / projects.androidApp Syntax
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "kmp-mobile-workshop"

include(":shared")
include(":androidApp")
