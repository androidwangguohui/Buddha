package com.example.buddha.util


import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build

class SoundPoolUtil(private val context: Context) {

    private var soundPool: SoundPool? = null
    private var soundId: Int = 0
    private var loaded = false

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            soundPool = SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            @Suppress("DEPRECATION")
            soundPool = SoundPool(1, android.media.AudioManager.STREAM_MUSIC, 0)
        }

        soundPool?.setOnLoadCompleteListener { _, _, status ->
            loaded = status == 0
        }
    }

    fun loadSound(resId: Int) {
        soundId = soundPool?.load(context, resId, 1) ?: 0
    }

    fun playSound() {
        if (loaded) {
            soundPool?.play(soundId, 1f, 1f, 1, 0, 1f)
        }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}