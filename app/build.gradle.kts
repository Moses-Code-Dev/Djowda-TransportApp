/*
 *
 *  * Created by the Djowda Project Team
 *  * Copyright (c) 2017-2025 Djowda. All rights reserved.
 *  *
 *  * This file is part of the Djowda Project.
 *  *
 *  * Licensed under the GNU Affero General Public License v3.0 (AGPL-3.0)
 *  *
 *  * You may redistribute and/or modify this file under the terms of the
 *  * GNU Affero General Public License as published by the Free Software Foundation,
 *  * either version 3 of the License, or (at your option) any later version.
 *  *
 *  * This file is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU Affero General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU Affero General Public License
 *  * along with this file. If not, see <https://www.gnu.org/licenses/>.
 *  *
 *  * Notes:
 *  * - The Djowda Project is a community-driven initiative evolving toward a decentralized food ecosystem.
 *  * - Contributions are welcome under the AGPL terms.
 *  *
 *  * Last Modified: 2025-10-28 13:54
 *
 *
 */

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