package com.example.buddha.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.Gravity
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import com.example.buddha.R
import com.example.buddha.databinding.ActivityMuyuBinding
import com.example.buddha.util.SoundPoolUtil

class MuYuActivity: BaseActivity() {

    private lateinit var soundPoolUtil: SoundPoolUtil

    private lateinit var  mActivityMuyuBinding:ActivityMuyuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMuyuBinding = ActivityMuyuBinding.inflate(layoutInflater)
        setContentView(mActivityMuyuBinding.root)
        // 初始化音效工具
        soundPoolUtil = SoundPoolUtil(this)
        soundPoolUtil.loadSound(R.raw.wooden_fish_tap1)
        //初始化动画json
//        mActivityMuyuBinding.lottieWoodenFish.setAnimation(R.raw.wooden_fish_animation)
        // 设置木鱼点击事件
        mActivityMuyuBinding.lottieWoodenFish.setOnClickListener {
            performWoodenFishTap()
        }
    }

    private var count = 0
    private val meritTexts = arrayOf("功德+1", "善哉善哉", "阿弥陀佛", "福报深厚", "平安顺遂")

    private fun performWoodenFishTap() {
        // 增加计数
        count++
        mActivityMuyuBinding.tvCount.text = "功德: $count"
        //先重置动画
        mActivityMuyuBinding.lottieWoodenFish.cancelAnimation()
        mActivityMuyuBinding.lottieWoodenFish.progress = 0f
        // 播放木鱼敲击动画
        mActivityMuyuBinding.lottieWoodenFish.playAnimation()
//        mActivityMuyuBinding.lottieWoodenFish.repeatCount = 0//不重复
//        mActivityMuyuBinding.lottieWoodenFish.speed  = 1.0f//速度
        // 播放敲击音效
        soundPoolUtil.playSound()
        // 显示浮动功德文字
        showFloatingMeritText()
        mActivityMuyuBinding.iv.setOnClickListener {

        }
    }

    private fun showFloatingMeritText() {
        // 创建功德文字TextView
        val textView = TextView(this)
        textView.text = meritTexts.random()
        textView.setTextColor(android.graphics.Color.WHITE)
        textView.textSize = 18f
        textView.setShadowLayer(2f, 0f, 0f, android.graphics.Color.BLACK)
        textView.gravity = Gravity.CENTER

        // 设置布局参数
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.CENTER

        // 添加到容器
        mActivityMuyuBinding.containerMeritTexts.addView(textView, params)

        // 设置动画
        val startY = 0f
        val endY = -200f

        // 位移动画
        val translateYAnimator = ObjectAnimator.ofFloat(textView, "translationY", startY, endY)
        translateYAnimator.duration = 1500
        translateYAnimator.interpolator = AccelerateDecelerateInterpolator()

        // 透明度动画
        val alphaAnimator = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f)
        alphaAnimator.duration = 1000
        alphaAnimator.startDelay = 500

        // 缩放动画
        val scaleAnimator = ObjectAnimator.ofFloat(textView, "scaleX", 1f, 1.2f)
        scaleAnimator.duration = 300
        scaleAnimator.repeatCount = 1
        scaleAnimator.repeatMode = ValueAnimator.REVERSE

        // 组合动画
        val animatorSet = android.animation.AnimatorSet()
        animatorSet.playTogether(translateYAnimator, alphaAnimator, scaleAnimator)

        // 动画结束后移除View
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mActivityMuyuBinding.containerMeritTexts.removeView(textView)
            }
        })

        // 启动动画
        animatorSet.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        // 释放音效资源
        soundPoolUtil.release()
    }
}