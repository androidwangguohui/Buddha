pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        flatDir {
//            dirs("libs")
//        }
//        flatDir {
//            dirs("./libs/adn")
//        }
//        flatDir {
//            dirs("./libs/adapter")
//        }
        maven ("https://artifact.bytedance.com/repository/pangle" )
        maven ("https://artifact.bytedance.com/repository/AwemeOpenSDK")
        maven ("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_support/" )
        maven(url = "https://jitpack.io")
        google()
        mavenCentral()
    }
}

rootProject.name = "Buddha"
include(":app")
 