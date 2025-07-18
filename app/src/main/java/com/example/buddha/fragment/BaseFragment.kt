package com.example.buddha.fragment

import android.content.Intent


open class BaseFragment : androidx.fragment.app.Fragment() {


    open fun startActivity(mAppCompatActivity:Class<*>){
        var intent = Intent(requireActivity(),mAppCompatActivity)
        startActivity(intent)
    }

    open fun startActivity(mAppCompatActivity:Class<*>,name:String,int:Int){
        var intent = Intent(requireActivity(),mAppCompatActivity)
        intent.putExtra(name,int)
        startActivity(intent)
    }

    open fun startActivity(mAppCompatActivity:Class<*>,name:String,int:String){
        var intent = Intent(requireActivity(),mAppCompatActivity)
        intent.putExtra(name,int)
        startActivity(intent)
    }
}