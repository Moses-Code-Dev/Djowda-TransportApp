plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.djowda.djowdamap"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":Shared_res"))

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    implementation(libs.glide)
    implementation(libs.lifecycle.livedata)

    annotationProcessor(libs.compiler)
    implementation(libs.glide.transformations)


    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}