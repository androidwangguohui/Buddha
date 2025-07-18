package com.example.buddha.activity

import android.os.Bundle
import com.example.buddha.databinding.ActivityLoginBinding

class LoginActivity: BaseActivity() {

    private lateinit var mActivityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mActivityLoginBinding.root)

    }
}