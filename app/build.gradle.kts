plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.navigation.safearg)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)

}

android {
    namespace = "com.thierrystpierre.rides"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thierrystpierre.rides"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            storeFile = file("/home/thierry/AndroidStudioProjects/keyStore/keystore.jks")
            storePassword = "K3ySt0r3"
            keyAlias = "mainKey"
            keyPassword = "K3yP4ss"
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.bundles.ktor)
    implementation(libs.kotlin.serialization)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    kaptTest(libs.hilt.test.compiler)
    testImplementation(libs.hilt.android.test)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation (libs.coroutine.test)
    testImplementation(libs.core.testing)
    ksp(libs.core.testing)

    implementation(libs.hilt.lib)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.androidx.compiler)
}