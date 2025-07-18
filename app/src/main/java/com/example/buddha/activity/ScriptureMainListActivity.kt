package com.example.buddha.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buddha.adapter.ItemClickListener
import com.example.buddha.adapter.MainScriptureAdapter
import com.example.buddha.databinding.ActivityMainScriptureBinding

class ScriptureMainListActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mActivityMainScriptureBinding:ActivityMainScriptureBinding = ActivityMainScriptureBinding.inflate(layoutInflater)
        setContentView(mActivityMainScriptureBinding.root)

        // 创建可变列表（可以添加、删除元素）
        val mutableList: MutableList<String> = mutableListOf("apple", "banana")
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
        mutableList.add("cherry") // 添加元素
//        mutableList.remove("apple") // 删除元素
        mActivityMainScriptureBinding.scriptureRecyclerView.layoutManager = LinearLayoutManager(this)
        mActivityMainScriptureBinding.scriptureRecyclerView.adapter =
            MainScriptureAdapter(mutableList, object : ItemClickListener {
                override fun onItemClick(position: Int) {
                    startActivity(ScriptureDetailsActivity::class.java,"position", position)
                }
            }
         )
    }
}