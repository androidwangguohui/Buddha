package com.example.buddha.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buddha.activity.ScriptureMainListActivity
import com.example.buddha.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {

    private var param1: String? = null
    private lateinit var mFragmentHomeBinding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        return  mFragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        mFragmentHomeBinding.textView.text = param1
        mFragmentHomeBinding.textView.setOnClickListener {
            startActivity(ScriptureMainListActivity::class.java)
        }

    }

    companion object {
        private const val ARG_PARAM1 = "param1"

        @JvmStatic
        fun newInstance(param1: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}