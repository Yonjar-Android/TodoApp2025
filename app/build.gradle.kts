
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
    id("kotlin-kapt")


}

android {
    namespace = "com.example.todoapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.todoapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.todoapp.AndroidCustomRunner"
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

    packaging{
        resources.excludes.add("META-INF/LICENSE.md") // Excluye el archivo LICENSE.md
        resources.excludes.add("META-INF/LICENSE-notice.md") // Excluye otros archivos de licencia si es necesario
        resources.excludes.add("META-INF/AL2.0") // Excluye archivos de licencia adicionales
        resources.excludes.add("META-INF/LGPL2.1") // Excluye archivos de licencia adicionales
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
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Gráficos
    implementation (libs.compose.charts)

    // Room (Base de datos y persistencia de datos)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)

    // optional - Test helpers
    testImplementation(libs.androidx.room.testing)

    // optional - Paging 3 Integration
    implementation(libs.androidx.room.paging)

    // Dagger Hilt

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // For instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    // ...with Kotlin.
    kaptAndroidTest(libs.hilt.android.compiler)

    // Testing Mock

    testImplementation(libs.mockk)

    // Test coroutine testing

    testImplementation(libs.kotlinx.coroutines.test)

    // Splash Screen

    implementation(libs.androidx.core.splashscreen)

    // Color picker

    implementation(libs.compose.colorpicker)
    testImplementation(kotlin("test"))

}

kapt {
    correctErrorTypes = true
}