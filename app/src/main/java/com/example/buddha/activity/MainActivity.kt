package com.example.buddha.activity

import android.content.Context
import android.os.Bundle
import com.bytedance.sdk.openadsdk.TTAdConfig
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTCustomController
import com.bytedance.sdk.openadsdk.mediation.init.MediationPrivacyConfig
import com.example.buddha.adapter.FragmentViewPagerAdapter
import com.example.buddha.databinding.ActivityMainBinding
import com.example.buddha.util.ToastUtil
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity() {

    // 声明一个私有的 ActivityMainBinding 类型的变量 binding
    private lateinit var mActivityMainBinding: ActivityMainBinding
    // 记录第一次点击返回键的时间
    private var backPressedTime: Long = 0
    // 两次点击的间隔时间（毫秒）
    private val BACK_PRESS_INTERVAL: Long = 2000
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // 判断两次点击的时间间隔
        if (backPressedTime + BACK_PRESS_INTERVAL > System.currentTimeMillis()) {
            // 两次点击时间间隔小于设定值，退出程序
            super.onBackPressed()
            return
        } else {
            // 第一次点击或两次点击间隔超过设定值，提示用户
            ToastUtil.showShort(this,"再按一次返回键退出程序")
        }
        // 更新点击时间
        backPressedTime = System.currentTimeMillis()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mActivityMainBinding.root)

        // 设置ViewPager适配器
        val adapter = FragmentViewPagerAdapter(this)
        mActivityMainBinding.viewPager.adapter = adapter

        // 设置TabLayout与ViewPager联动
        TabLayoutMediator(mActivityMainBinding.tabLayout, mActivityMainBinding.viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "首页"
                1 -> "发现"
                2 -> "我的"
                else -> ""
            }
        }.attach()
        //初始化广告
        initChuanShanJia()
    }

//    ①.useMediation仅可设置一次，不支持后续二次修改。
//    ②.useMediation(false)默认关闭聚合功能，若useMediation设置为false，默认不使用聚合，仅使用穿山甲SDK功能。
//    ③.默认仅支持初始化SDK一次，多次初始化SDK以第一次初始化为准。
//    ④.TTAdSdk中IMediationManager getMediationManager() 需确保SDK初始化完成后调用，否则可能为空。
//    ⑤.SDK初始化成功回调转到了子线程，请不要直接在回调里做UI操作。
//    ⑥.appId为必填内容，若appid是通过服务端下发的，那么在初始化前需要做不为空的判断。
//    ⑦.开发者需确保在success回调之后再去请求广告。
//    ⑧.SDK初始化API：该API必须在主线程中调用，SDK会将初始化操作放在子线程执行。
//    ⑨.如需开启多进程可在初始化时设置.supportMultiProcess(true) ，默认false；注意：开启多进程开关时需要将ADN的多进程也开启，否则广告展示异常，影响收益。CSJ、gdt无需额外设置，KS、baidu、Sigmob、Mintegral需要在清单文件中配置各家ADN激励全屏xxxActivity属性android:multiprocess="true"；
//    ⑩.SDK隐私协议地址：
//    1. 融合SDK隐私政策：https://www.csjplatform.com/terms/gromoresdk-privacy
//    2. 穿山甲广告SDK隐私政策：https://www.csjplatform.com/privacy
//    3. 默认值为false，需重写TTCustomController函数返回值
    private fun initChuanShanJia() {
        TTAdSdk.init(this, buildConfig(this))
        TTAdSdk.start(object : TTAdSdk.Callback {
            override fun success() {
                //初始化成功
                //在初始化成功回调之后进行广告加载
                ToastUtil.showShort(this@MainActivity,"初始化广告成功")
            }

            override fun fail(code: Int, msg: String?) {
                //初始化失败
                ToastUtil.showShort(this@MainActivity, "初始化广告失败$msg")
            }
        })
    }
    // 构造TTAdConfig
    private fun buildConfig(context: Context): TTAdConfig {
        return TTAdConfig.Builder()
            .appId("5719708") //APP ID 790562
            .appName("Buddha") //APP Name
            .useMediation(true)  //开启聚合功能
            .debug(false)  //关闭debug开关
            .themeStatus(0)  //正常模式  0是正常模式；1是夜间模式；
            /**
             * 多进程增加注释说明：V>=5.1.6.0支持多进程，如需开启可在初始化时设置.supportMultiProcess(true) ，默认false；
             * 注意：开启多进程开关时需要将ADN的多进程也开启，否则广告展示异常，影响收益。
             * CSJ、gdt无需额外设置，KS、baidu、Sigmob、Mintegral需要在清单文件中配置各家ADN激励全屏xxxActivity属性android:multiprocess="true"
             */
            .supportMultiProcess(false)  //不支持
            .customController(getTTCustomController())  //设置隐私权
            .build()
    }
    //设置隐私合规
    private fun getTTCustomController(): TTCustomController? {
        return object : TTCustomController() {
            override fun isCanUseLocation(): Boolean {  //是否授权位置权限
                return true
            }

            override fun isCanUsePhoneState(): Boolean {  //是否授权手机信息权限
                return true
            }

            override fun isCanUseWifiState(): Boolean {  //是否授权wifi state权限
                return true
            }

            override fun isCanUseWriteExternal(): Boolean {  //是否授权写外部存储权限
                return true
            }

            override fun isCanUseAndroidId(): Boolean {  //是否授权Android Id权限
                return true
            }

            override fun getMediationPrivacyConfig(): MediationPrivacyConfig? {
                return object : MediationPrivacyConfig() {
                    override fun isLimitPersonalAds(): Boolean {  //是否限制个性化广告
                        return false
                    }

                    override fun isProgrammaticRecommend(): Boolean {  //是否开启程序化广告推荐
                        return true
                    }
                }
            }
        }
    }

}