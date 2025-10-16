plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.djowda.djowdaTransport"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.djowda.djowdaTransport"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.glide)

    annotationProcessor(libs.compiler)

    implementation(libs.core.splashscreen)
    implementation(libs.room.runtime)

    annotationProcessor(libs.room.compiler)

    implementation(libs.play.services.location)
    implementation(libs.gson)


    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}