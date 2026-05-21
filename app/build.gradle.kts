plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.weather_java"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weather_java"
        minSdk = 24
        targetSdk = 35
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.google.android.gms:play-services-location:21.2.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ✅ Use matching Glide version
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // ✅ Remove nested dependencies block
    implementation("com.github.amitshekhariitbhu.Fast-Android-Networking:android-networking:1.0.4") {
        exclude("com.android.support", "support-compat")
    }
    implementation("com.airbnb.android:lottie:6.6.6")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}
