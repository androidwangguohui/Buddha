package com.example.buddha.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.CSJAdError
import com.bytedance.sdk.openadsdk.CSJSplashAd
import com.bytedance.sdk.openadsdk.TTAdNative
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.example.buddha.databinding.FragmentFunctionBinding
import com.example.buddha.util.ToastUtil

class FunctionFragment: BaseFragment() {

    private var param1: String? = null
    private lateinit var mFragmentFunctionBinding: FragmentFunctionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mFragmentFunctionBinding = FragmentFunctionBinding.inflate(inflater,container,false)
        return mFragmentFunctionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }

        mFragmentFunctionBinding.textView.text = param1
        mFragmentFunctionBinding.textView.setOnClickListener {
            mFragmentFunctionBinding.rr.visibility = View.VISIBLE
            loadSplashAd(requireActivity(),mFragmentFunctionBinding.rr)
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"

        @JvmStatic
        fun newInstance(param1: String) =
            FunctionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    //构造开屏广告的Adslot
    fun buildSplashAdslot(): AdSlot {
        return AdSlot.Builder()
            .setCodeId("103545267") //广告位ID
//            .setImageAcceptedSize(640, 320)
            //[start支持模板样式]:需要支持模板广告和原生广告样式的切换，需要调用supportRenderControl和setExpressViewAcceptedSize
//            .supportRenderControl() //支持模板样式
//            .setExpressViewAcceptedSize(350f,300f)//设置模板宽高（dp）
            //[end支持模板样式]
//            .setAdCount(3) //请求广告数量为1到3条
            .build()
    }

//    ①.图片尺寸传入与展示区域大小设置需保持一致，避免素材变形。
//    ②.需要确保在SDK初始化成功后再进行广告请求，否则可能导致广告请求加载失败。
//    ③.聚合SDK是通过广告位ID发起广告请求的，切记不要使用混淆；（什么是广告位：https://www.csjplatform.com/supportcenter/5841）
//    ④.在广告接入前需要明确各ADN对应GroMore广告样式情况，以确保正确完成广告接入，避免由于广告类型不匹配导致接入报错等情况的发生； （各ADN对应GroMore广告样式:https://www.csjplatform.com/supportcenter/27654）
//    ⑤.若使用SDK缓存功能，需确保开发者未自行实现缓存逻辑，否则可能存在缓存逻辑冲突且开屏广告由于加载时机过早，不建议开发者开屏广告使用preload首次预缓存功能。注意：使用SDK预缓存功能时（媒体平台瀑布流属性设置开关控制），需确保本次广告请求参数和上一次广告请求参数设置保持一致，否则缓存不生效。
//    ⑥.由于各广告平台对于包名校验规则不同，需确保在穿山甲媒体平台填写的包名符合各ADN平台规范，避免由于包名校验不匹配导致的无广告返回情况的产生。
//    ⑦.需要开发者在开屏Activity中onStop()中做一个标记 在onResume()中做跳转主页面的逻辑处理，跳转之后开屏控件上的view移除。
//    @Override
//    protected void onResume() {
//        //判断是否该跳转到主页面
//        if (mForceGoMain) {
//            goToMainActivity();
//        }
//        super.onResume();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mForceGoMain = true;
//    }
//    ⑧.在接入过程中或者线上用户最常反馈的就是广告无填充问题，当遇到这类问题时，聚合维度排查建议：
//    - 及时查看相关错误码埋点情况或根据穿山甲媒体平台找到GroMore->应用管理->搜索对应的广告位/代码位ID->点击三个点->点击诊断分析；根据诊断分析解释说明协助快速定位并解决问题。
//    - 当通过诊断分析无法定位问题时，可通过抓包或日志检索关键字TMe或mediation明确adn具体错误码。（抓包方法：https://www.csjplatform.com/support/doc/5fd6ca37c09662001ce316cd）
//    - 支持通过TTxxxAd.getAdLoadInfo(), 获取加载失败的adn错误信息，根据LoadInfoList详情发现并定位问题。

    // 加载开屏广告
    fun loadSplashAd(act: Activity,splashContainer :ViewGroup) {
        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(act)
        adNativeLoader.loadSplashAd(buildSplashAdslot(), object : TTAdNative.CSJSplashAdListener {

            override fun onSplashLoadSuccess(p0: CSJSplashAd?) {
//                ToastUtil.showShort(requireActivity(),"")
            }

            override fun onSplashLoadFail(error: CSJAdError?) {
                //广告加载失败
                ToastUtil.showShort(requireActivity(), "加载失败："+error?.msg)
            }

            override fun onSplashRenderSuccess(csjSplashAd: CSJSplashAd?) {
                //广告渲染成功，在此展示广告
                showSplashAd(csjSplashAd, splashContainer); //注 ：splashContainer为展示Banner广告的容器
            }

            override fun onSplashRenderFail(p0: CSJSplashAd?, p1: CSJAdError?) {
                //广告渲染失败
                ToastUtil.showShort(requireActivity(), "渲染失败："+p1?.msg)
            }
        }, 3500)
    }

    //展示开屏广告
    fun showSplashAd(ad: CSJSplashAd?, container: ViewGroup?) {
        ad?.let {
            it.setSplashAdListener(object : CSJSplashAd.SplashAdListener {
                override fun onSplashAdShow(csjSplashAd: CSJSplashAd?) {
                    //广告展示
                    //获取展示广告相关信息，需要再show回调之后进行获取
                    var manager = it.mediationManager;
                    if (manager != null && manager.showEcpm != null) {
                        val ecpm = manager.showEcpm.ecpm //展示广告的价格
                        val sdkName = manager.showEcpm.sdkName  //展示广告的adn名称
                        val slotId = manager.showEcpm.slotId //展示广告的代码位ID
                    }
                }

                override fun onSplashAdClick(csjSplashAd: CSJSplashAd?) {
                    //广告点击
                    ToastUtil.showShort(requireActivity(),"点击广告")
                }

                override fun onSplashAdClose(csjSplashAd: CSJSplashAd?, p1: Int) {
                    //广告关闭
                    ToastUtil.showShort(requireActivity(),"关闭广告")
                    mFragmentFunctionBinding.rr.visibility = View.GONE
                }

            })
            if (container != null) {
                it.showSplashView(container) //展示开屏广告
            }
        }
    }
}