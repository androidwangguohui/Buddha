// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

}

buildscript {
    repositories {
        //mintegral sdk依赖   引入mintegral sdk需要添加此maven
        maven { url = uri("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_support/") }
        //穿山甲融合SDK依赖
        maven { url = uri("https://artifact.bytedance.com/repository/pangle") }
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
        //Gromore自动拉取 Adapre AAR 插件脚本
        classpath("com.pangle.cn:mediation-auto-adapter:1.0.3")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}