plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.bibliotecapmdm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.bibliotecapmdm"
        minSdk = 24
        targetSdk = 34
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity)



    implementation(libs.core.ktx.v1120)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.material.v1100)
    implementation(libs.androidx.constraintlayout.v214)

    // Room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Glide
    implementation(libs.glide)
    ksp(libs.compiler.v4142)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)


    implementation(libs.androidx.navigation.fragment.ktx.v275)
    implementation(libs.androidx.navigation.ui.ktx.v275)

    // Gson
    implementation(libs.gson)
}