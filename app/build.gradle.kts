import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
}

android {
    namespace = "ru.plumsoftware.coffeeapp"
    compileSdk = 36


    val openAdsId = gradleLocalProperties(rootDir, providers).getProperty("openAdsId")
    val interstitialAdsId =  gradleLocalProperties(rootDir, providers).getProperty("interstitialAdsId")

    defaultConfig {
        applicationId = "ru.plumsoftware.coffeeapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 7
        versionName = "1.0.7"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(type = "String", name = "openAdsId", value = "\"${openAdsId}\"")
        buildConfigField(type = "String", name = "interstitialAdsId", value = "\"${interstitialAdsId}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
        android.buildFeatures.buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val yandex_ads_version = "7.14.1"
    val ui_controller = "0.32.0"
    val koin_version = "4.0.2"
    val nav_version = "2.7.7"
    val room_version = "2.6.1"
    val ksp_version = "2.6.1"

    //Auto generate
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform("androidx.compose:compose-bom:2025.01.01"))
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

    //KSP
    ksp("androidx.room:room-compiler:$ksp_version")

    //Firebase Messaging
    implementation("com.google.firebase:firebase-messaging:24.1.0")
    implementation("com.google.firebase:firebase-inappmessaging-display:21.0.1")

    //Corner smoothing
    implementation("com.github.racra:smooth-corner-rect-android-compose:v1.0.0")

    //Modules
    implementation(project(path = ":data"))
    implementation(project(path = ":domain"))
    implementation(project(path = ":coffee"))
}