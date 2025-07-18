plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("mediation-auto-adapter")
}

android {
    namespace = "com.example.buddha"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.buddha"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    buildFeatures {
        viewBinding = true
    }

    //ndkVersion = "23.1.7779620"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // 引用RecyclerView库，版本号可按需调整
    implementation("androidx.recyclerview:recyclerview:1.3.0")

    // 如需使用GridLayoutManager或StaggeredGridLayoutManager
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")

    //首页viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.google.android.material:material:1.9.0")

    // 穿山甲融合SDK
    implementation("com.pangle.cn:mediation-sdk:6.9.1.7")

    //敲木鱼动画
    implementation("com.airbnb.android:lottie:6.1.0")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //加载gif
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.27")

    //emoji
//    implementation ("com.github.w446108264:XhsEmoticonsKeyboard:2.0.4")

    //FFmpegKit依赖
//    implementation ("com.arthenica:ffmpeg-kit-full:5.1.LTS")

//    //注：adn如果通过aar方式引入，需要把对应的adn aar放到libs目录下，注意aar名称和版本号需要和下面命令行匹
//    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
}
//
//// 中介自动适配器配置
//mediationAutoAdapter {
//    //自动适配adn 所有adn adapter，默认值为true，如果为false脚本功能全部关闭
//    setAutoUpdate(true) // 假设存在这样的公开方法
//    //如果不想全部自动适配，可选择此项，默认值为空[]，可以不填写
//    //如果autoUpdate设置成false，此项不生效，不会自动适配adn adapter
//    //如果autoUpdate设置成true，autoUpdateAdn配置了adn，则生效配置的adn。
//    //autoUpdateAdn没有配置adn，自动适配所有的adn
//    autoUpdateAdn = listOf("gdt", "baidu", "ks", "admob", "mtg", "sigmob", "unity")
//}
