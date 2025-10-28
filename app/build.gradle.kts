plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.djowda.djowdaTransport"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.djowda.djowdaTransport"
        minSdk = 27
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

    signingConfigs {
        create("release") {
            // Use the keystore created dynamically by GitHub Actions
            val keystoreFile = rootProject.file("release.keystore") // path relative to root
            if (!keystoreFile.exists()) {
                println("WARNING: release.keystore not found! Build may fail on CI.")
            }
            storeFile = keystoreFile
            storePassword = System.getenv("RELEASE_STORE_PASSWORD")
            keyAlias = System.getenv("RELEASE_KEY_ALIAS")
            keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(project(":Shared_res"))
    implementation(project(":DjowdaMap"))

    implementation(libs.core.splashscreen)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation(libs.whynotimagecarousel)
    implementation(libs.glide)
    implementation(libs.lifecycle.livedata)

    annotationProcessor(libs.compiler)
    implementation(libs.glide.transformations)

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    implementation(libs.libphonenumber)


    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}