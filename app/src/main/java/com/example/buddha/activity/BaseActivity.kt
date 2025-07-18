package com.example.buddha.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //隐藏状态栏
        enableEdgeToEdge()
//        // 检查版本是否大于等于 Android 5.0 (API 21)
//        val window = getWindow()
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = Color.TRANSPARENT

    }

    open fun startActivity(mAppCompatActivity:Class<*>){
        var intent = Intent(this,mAppCompatActivity)
        startActivity(intent)
    }

    open fun startActivity(mAppCompatActivity: Class<*>, name:String, int:Int){
        var intent = Intent(this,mAppCompatActivity)
        intent.putExtra(name,int)
        startActivity(intent)
    }

    open fun startActivity(mAppCompatActivity:Class<*>,name:String,int:String){
        var intent = Intent(this,mAppCompatActivity)
        intent.putExtra(name,int)
        startActivity(intent)
    }
}