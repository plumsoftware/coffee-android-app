buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath(kotlin("gradle-plugin", version = "1.9.25"))
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.9.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("com.android.library") version "8.9.1" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.25" apply false
}