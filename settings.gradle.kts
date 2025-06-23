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
        google()
        mavenCentral()
    }
}

rootProject.name = "android-chat-app"
include(":app")
include(":core:ui")
include(":feature:auth")
include(":data:network")
include(":core:domain")
include(":data:datastore")
include(":data:socketio")
include(":feature:users")
include(":feature:chat")
include(":core:model")
include(":core:appevent")
