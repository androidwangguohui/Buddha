package com.example.buddha.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buddha.activity.MuYuActivity
import com.example.buddha.databinding.FragmentMyBinding

class MyFragment : BaseFragment() {

    private var param1: String? = null
    private lateinit var mFragmentMyBinding: FragmentMyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFragmentMyBinding = FragmentMyBinding.inflate(inflater,container,false)

        return mFragmentMyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentMyBinding.textView.text = param1
        mFragmentMyBinding.textView.setOnClickListener {
            startActivity(MuYuActivity::class.java)
        }
    }


    companion object {
        private const val ARG_PARAM1 = "param1"
        @JvmStatic
        fun newInstance(param1: String) = MyFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }
        }

    }
}