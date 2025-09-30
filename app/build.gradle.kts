plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.2.20"
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.bible"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.bible"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.cronet.embedded)
    implementation(libs.androidx.compose.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation ("androidx.room:room-runtime:2.8.0")
    implementation ("androidx.room:room-ktx:2.8.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.1.1")
    annotationProcessor ("androidx.room:room-compiler:2.8.0")
    ksp("androidx.room:room-compiler:2.8.0")

    implementation("com.google.code.gson:gson:2.13.2")
    implementation("com.composables:icons-lucide:1.0.0")

    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.1.2") // Autenticação
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.1.2") // Banco (CRUD)
    implementation("io.github.jan-tennert.supabase:realtime-kt:2.1.2")
    implementation(platform("io.github.jan-tennert.supabase:bom:2.1.2"))
    implementation("io.ktor:ktor-client-okhttp:2.3.4")


}
