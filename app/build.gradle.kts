plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
}

android {
    namespace = "ru.plumsoftware.coffeeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.plumsoftware.coffeeapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 5
        versionName = "1.0.5"

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

    val yandex_ads_version = "7.4.0"
    val ui_controller = "0.32.0"
    val koin_version = "3.4.2"
    val nav_version = "2.7.7"
    val room_version = "2.6.1"
    val kapt_version = "2.6.1"

    //Auto generate
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform("androidx.compose:compose-bom:2024.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Yandex Ads
    implementation("com.yandex.android:mobileads:$yandex_ads_version")

    //UI controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:$ui_controller")

    //Koin
    implementation("io.insert-koin:koin-android:$koin_version")
    implementation("io.insert-koin:koin-core:$koin_version")

    //Navigation
    implementation("androidx.navigation:navigation-common:$nav_version")
    implementation("androidx.navigation:navigation-compose:$nav_version")

    //Room database
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //Kapt
    ksp("androidx.room:room-compiler:$kapt_version")

    //Firebase Messaging
    implementation("com.google.firebase:firebase-messaging:24.0.1")
    implementation("com.google.firebase:firebase-inappmessaging-display:21.0.0")

    //Corner smoothing
    implementation("com.github.racra:smooth-corner-rect-android-compose:v1.0.0")

    //Modules
    implementation(project(path = ":data"))
    implementation(project(path = ":domain"))
    implementation(project(path = ":coffee"))
}