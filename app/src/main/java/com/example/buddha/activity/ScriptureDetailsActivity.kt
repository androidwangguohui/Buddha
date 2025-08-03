package com.example.buddha.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.example.buddha.databinding.ActivityScriptureDetailBinding
import com.example.buddha.util.Constant
import com.example.buddha.util.SharedPreferencesHelper
import com.example.buddha.util.TextUtil
import com.example.buddha.util.ToastUtil
import java.util.Locale


class ScriptureDetailsActivity: BaseActivity(), TextToSpeech.OnInitListener {

    private var scrollY = 0
    private var position = 0
    private lateinit var mSharedPreferencesHelper :SharedPreferencesHelper
    private lateinit var tts: TextToSpeech
    private lateinit var mActivityScriptureDetailBinding :ActivityScriptureDetailBinding
    private var startTimeMillis: Long = 0
    private var startTimeMillis2: Long = 0
    private var startTimeMillis3: Long = 0
    private var startTimeMillis4: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityScriptureDetailBinding  = ActivityScriptureDetailBinding.inflate(layoutInflater)
        setContentView(mActivityScriptureDetailBinding.root)
        position = intent.getIntExtra("position",0)
        mSharedPreferencesHelper = SharedPreferencesHelper(this, Constant.SHAREDPREFERENCESHELPER)
        val y = mSharedPreferencesHelper.getSharedPreference(position.toString(),0)
        var yy : Int? = y as? Int
        mActivityScriptureDetailBinding.scriptureScrollView.post (object : Runnable{
            override fun run() {
                mActivityScriptureDetailBinding.scriptureScrollView.scrollTo(0,yy!!)
            }
        })
        mActivityScriptureDetailBinding.scriptureScrollView.viewTreeObserver.addOnScrollChangedListener {
            // 获取当前滚动位置
            val scrollX =  mActivityScriptureDetailBinding.scriptureScrollView.scrollX
            scrollY =  mActivityScriptureDetailBinding.scriptureScrollView.scrollY
            ToastUtil.showShort(this,scrollX.toString())
            ToastUtil.showShort(this,scrollY.toString())
        }
        // 初始化TextToSpeech引擎
        initTextToSpeech()
        //朗读逻辑
        setTextToSpeech()
        //返回按钮
        mActivityScriptureDetailBinding.backIv.setOnClickListener { finish() }
    }

    var ttsText:String = "先禅后密——本经在如来详说首楞严神咒之前，其教相主要是以禅为主，因为如来要以[显说]的方式，来引摄阿难等，先行破妄，而于真实之道得信解，并发心修行，故这一部分非要显说不可。于无上法得信解发心之后，再说密咒，及其修法，方便引摄，渐次入于如来甚深三密不可思议境界。是故，即是以禅入[信、解、行]之门，而以密入于深密修证之门，以是之故，本经在大藏经中，列于密教部，良有以也。\n" +
            "2.由禅入密——由对无上禅之闻、思，而得于如来藏不思议性心得开悟，复由如来三密威神之力加持，令行者顿得三密相应，顿入、顿证此不思议之本体。此即是本经由禅入密之处。\n" +
            "3.外禅内密——亦即外修首楞严定，内持心咒，如是即是禅密一体、外禅内密。\n" +
            "因此可知，本经之法门，殊胜无比。此外以本经教法而言，可说是：[非禅不得密，非密不证禅]。因为若光修密，而毫不习禅，则于无上理，无从信解、悟入，如是径修三密，则不知所云，即使遍学、遍修种种密法，也不知在修什么，因为无信、无解、无悟，则所修何事。所谓[悟而后修，不悟复何修，]而欲悟，则须先信解。因此古德说，修密须先有十年的显教基础，此话颇有道理。\n" +
            "其次，[非密不证禅]，以无上禅乃如来不可思议的[秘语秘意]境界，故非言思可得；是故不能以[思议法]而求于[不思议法]，须得径以[不思议法]而证入[不思议法]。是故，于禅得悟之后，即顿舍一切凡夫言说思议之法，而径以如来所开示之不思议[三密]法门为所依，以此而顿入如来三密不思议、万德庄严之境。\n" +
            "再次，于经教中，像本经这样圆摄禅净密律、显密性相诸法者，实不多见，其中以《华严经》最为明显；不过《华严经》虽圆摄显密性相诸法，但较偏于理，至于本经，则不但圆摄显密一切诸法，且理事均等。\n" +
            "又次，本经圆摄显密性相诸法，含有两层深义（即所谓[如来密义]）：\n" +
            "1.成就从生之具足信、决定信——何谓[具足信]，即具足信受如来所说一切显密性相诸法，等心修学，不以自妄想心而妄生分别、计着。何谓[决定信]，即决定谛信一切佛语；佛语谛实，决定不虚；不为一切凡外邪小所动。行者以得[具有信]及[决定信]故，满足信根，速入菩萨正位，而得十信满心，于佛法中住。\n" +
            "2.速断法执——以如法、等心修行一切法故，于诸法门，远离妄想分别、爱憎取舍；于初发心时，即顿断法执、法爱；法执断故，我见、我慢、我执亦断。我法二执断故，顿了本具清凉法体（所谓[不历僧祇获法身]），顿与如来萨婆若一切智海相应，堪修无量无上不可思议大行，入普贤行；是故当知，此法门者，即是熏习、长养、成熟、成就[菩萨种性]、[如来种性]，令佛种不断。是为[如来真实义]也；何以故，一切法皆为一佛乘故，非若二、若三、何况若五，是皆化城，而非宝所。\n" +
            "楞严真经无疑\n" +
            "摘自成观法师《大佛顶首楞严经义贯》。\n" +
            "问：【请师父慈悲开示：有人说《楞严经》是一部伪经，为什么，又，讨论《首楞严经》真伪之辨的文章或论文，可参看哪些刊物或书籍】\n" +
            "答：首先，为何近代有人说《首楞严经》是一部伪经，因为这是一部专门破魔的大宝典，令修行人得以辨魔、远离魔事，修行没有障难。而末法时期魔强法弱，故魔力令愚痴邪妄之人，谤此法门，令人不信，即无所依怙；此法门即被谤、被毁，众生不信此正法，魔于是得肆无忌惮，放心大胆地大造魔业，大成魔事，于是一切修行人不能出于魔掌，堕于三途，更遑论明心见性、修行菩提；故可知毁谤此经，即是毁去末法时期修行正法之人的最大靠山，窍取一切正修人士的护身符、以及破除魔事的无上法宝。因此，毁谤此经之事实，即末法一大魔事之肇端——魔尚未被破，已然先发制人，【抢先出招】了。恶心、邪心之人为魔所用，而发如是破法邪言，而一般大众，愚昧无知，不知轻重，也盲然随之应合。\n" +
            "其次，《首楞严经》是一部破魔大全的宝典，是诸魔的克星，有人说它是末法时期的一面[照妖镜]；一切佛法修行人，欲免于魔事，必须参究《楞严经》。又，末法时期，法欲灭时，这部《首楞严经》最先灭；因为《楞严经》一灭，诸魔横行，即无人能制，一切邪魔外道、邪师邪说便能肆无忌惮，横行无阻。如今末法已五百年，经法将灭时，即有邪心人，为魔所用，因而自疑经谤法，且令人疑经谤法；众生疑经谤法既久，共业成就，经法即灭，这世间便再也找不到这部经书了——法如是故，众生业报如是故：因为众生共谤如是经法，便再也无福见闻、领受如是经法。话说近世最先起来倡导疑谤《首楞严经》的，是民国初年几个白衣，后来亦时有所闻；有人在注解《楞严经》时，也自已主动提到，说：[有人说《首楞严经》是伪经]，但作者只提这么一笔就罢了，并非加以任何的评论或驳斥，是故徒启读者之疑窦，且又象是在[默认]或默许其说，于是更令读者心中狐疑不决，因而于此上妙法门即有障难：不能解，更不能悟入，遑论悟而后修。因此鄙人希望末世一切讲经注经者，若遇有人谤经毁法，应善为众生释疑、破疑、解惑，普令众生于如来正法生起正信、正解、正修行。这是讲解正法者的职责，这也才能达到讲经的目的；否则讲经何为。\n" +
            "后段“首楞严三昧法门的殊胜处”中所引之诸经论，便可用来作为“以经证经”的最佳例证（亦即，用其他佛所说经典的圣言，来证明本经与他经所说无异），故知《首楞严经》不但是一部佛口亲说的真经，而且是一部极为完备，圆满殊胜之经。隋代天台“智者”大师，即已闻说西天有此殊胜经法，便期盼此经赶快传来中土；其期盼之切，乃至造了一座“拜经台”，天天都亲自去西向拜经，盼其早来。然而尽智者大师一生，竟无缘亲见此经；因为印度的国王，深以此经为国宝，不听其外传他国；后经般剌密谛法师千方百计，在唐时偷渡传来，汉土众生才有缘得闻如是妙法。不过此宝典最后之所以得以传来中土，可能智者大师拜经的诚心也是一个增上缘。\n" +
            "东晋的高僧法显，于其所著《佛国记》（或称《高僧法显传》）中云：“佛昔于此说《首楞严》，法显生不值佛，但见遗迹处所而已。”\n" +
            "由此可见古人于佛、于法的信心之一斑，近世人根钝，且不知惜福，不知经法之可贵，真是“得了便宜又卖乖”；乃至依于世见、慢心，轻法慢教，疑谤正法，谓非真经，或曰非是佛说。试想：如是清净、胜妙法门，一字一句，读之都令人遍心清凉——如是智慧、妙法，除了佛的一切智外，还有谁能说得出，又，佛弟子不同于世间儒者或学问家，佛弟子或菩萨，若有所说、有所作，绝不会、也不须妄语假讬是佛所说，因为他们不须像孔子有“托古改制”的需要；他们没有什么“制”可以改、或须要改；诸菩萨、祖师大德，皆是承佛之教，弘传如来正法，其所作也都名正言顺的称为“论”，而为三藏之一，与佛所说的经藏一样受到尊重，受历代弘传；因此这些如来的贤圣弟子实在没有必要这样犯戒，欺诳如来及众生，而“伪造经典”——再想：若菩萨或祖师大德，已修到了能造出如是上妙经典、能开悟度化无量众生的境界，他还会去造这样的欺诳之业吗？他心中还会有如是欺诳的杂染心吗？连一般受五戒的居士都不会轻易妄语，更何况有如是智慧与修行境界的菩萨或祖师大德，会去犯这最基本的戒律，再说，如果菩萨已达到能宣说如是清净上妙之法的境界，他一定不会犯戒、伪造佛经，一定会按照正途而“造论”，且于其论中推仰如来，推尊如来修多罗教，宣扬如来修多罗之教旨，（这是一般论藏的通途），而不会“假装”自己是佛，而“假藉佛的名义”来说他自己的法，这种事，菩萨是干不出来的。\n" +
            "再者，我已如是称扬赞叹此首楞严法门，亦引诸经论加以称扬——我讲这么好的经给你听，你却来问我此经是真是假。若是伪经，我干嘛费这么大工夫为你讲。\n" +
            "身为佛弟子，深受佛恩，不知宝爱佛经之可贵，却听从恶知识来疑它、谤它，辜负如来。譬如有人被父母养育成人，一朝忽听恶人造谣，说他父亲不是他的亲生父，于是起疑，乃至不信亲父，甚而忘恩绝义，听信恶人谤法者亦如是，若因此疑而不信正法，忘佛深恩，即断绝善根，成一阐提人。\n" +
            "经中说：佛初成道时，魔王波旬即来见佛，劝请如来速入涅槃。他说：世尊所作已办，已证一切法无生，可以速入涅槃。\n" +
            "佛对波旬说：我诸弟子尚未成就，我不宜入涅槃。\n" +
            "波旬说：那么待世尊弟子皆成就时，应即入涅槃。经上又云：\n" +
            "[坚意，首楞严三昧如是无量，悉能示佛一切神力，无量众生皆得饶益。坚意，首楞严三昧不以一事、一缘、一义可知，一切禅定解脱三昧，神通如意无碍智慧，皆摄在首楞严中。譬如陂泉江河诸流皆入大海；如是，菩萨所有禅定，皆在首楞严三昧。譬如转轮圣王有大勇将，诸四种兵皆悉随从。坚意，如是所有三昧门、禅定门、辩才门、解脱门、陀罗尼门、神通门、明解脱门，是诸法门，悉皆摄在首楞严三昧，随有菩萨行首楞严三昧，一切三昧皆悉随从，是故此三昧名为首楞严。]\n" +
            "佛告坚意：[菩萨住首楞严三昧，不行求财而以布施，大千世界及诸大海、天宫、人间，所有宝物、饮食、衣服、象马车乘，如是等物自在施与；此皆是本功德所致，况以神力随意所作，是名菩萨住首楞严三昧檀波罗蜜本事果报。]（此即谓：菩萨若住于首楞严三昧，若想行布施，虽然不去求财物，也不用神通力变化，但一切财物都能自然获得、自在布施与人）。\n" +
            "经中又云：\n" +
            "[若善男子善女人求佛道者，闻首楞严三昧义趣，信解不疑，当知是人于佛道不复退转；何况信已受持读诵，为他人说，如说修行。]\n" +
            "可见首楞严法门不但对大菩萨有大利益，连一般佛弟子，亦能令得[菩提心不退]的大利。至于其他利益，则在本经第七卷中，如来于重说咒后，有极详尽的开示，请参阅。\n" +
            "可见首楞严法门不但对大菩萨有大利益，连一般佛弟子，亦能令得[菩提心不退]的大利。至于其他利益，则在本经第七卷中，如来于重说咒后，有极详尽的开示，请参阅。\n" +
            "可见首楞严法门不但对大菩萨有大利益，连一般佛弟子，亦能令得[菩提心不退]的大利。至于其他利益，则在本经第七卷中，如来于重说咒后，有极详尽的开示，请参阅。\n" +
            "5.《大集地藏十轮经》卷一，佛向好疑问菩萨解答及说明地藏菩萨的功德威力，佛云：\n"+
            "5.《大集地藏十轮经》卷一，佛"


    private fun setTextToSpeech() {
        mActivityScriptureDetailBinding.readTextview.setOnClickListener {
            ToastUtil.showLong(this,ttsText.length.toString())
            if(mActivityScriptureDetailBinding.readTextview.text.toString() =="朗读"){
                startRead()
            }else{
                pauseRead()
            }
        }
    }

    //开始朗读逻辑
    private fun startRead() {
        mActivityScriptureDetailBinding.readTextview.text = "停止朗读"
        isRead = true
        if(!isPause){
            //获取第一次开始朗读时间
            startTimeMillis = System.currentTimeMillis() / 1000
        }else{
            //获取暂停后重新开始朗读时间
            startTimeMillis3 = System.currentTimeMillis() / 1000
        }
        val result = tts.isLanguageAvailable(Locale.CHINA)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            // 处理不支持的语言情况
            ToastUtil.showLong(this,"无法播放，请稍后重试")
        } else {
            // 设置语言并朗读文本
            tts.language = Locale.CHINA
            if(isPause){ //说明是暂停后重新开始朗读
                //暂停时间减掉第一次开始朗读时间   再去掉暂停期间的时间  就是朗读所用时间
                var readTime = (startTimeMillis2 - startTimeMillis)
                //暂且估算一秒读三个字
                var readSize =  readTime*3
                if(ttsText.length > readSize && startTimeMillis2 > startTimeMillis){
                    val params = Bundle()
                    params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "uniqueId")
                    tts.speak(ttsText.drop(readSize.toInt()), TextToSpeech.QUEUE_FLUSH, params, "uniqueId")
                }else{
                        ToastUtil.showShort(this,"朗读失败请稍后重试，请稍后重试")
                }
            }else{
                // 第一次开始朗读
                val params = Bundle()
                params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "uniqueId")
                tts.speak(ttsText, TextToSpeech.QUEUE_FLUSH, params, "uniqueId")
            }
        }
    }

    //手动触发暂停标识
    var isPause: Boolean = false
    //页面失去焦点的暂停标识
    var isRead: Boolean = false
    //暂停朗读逻辑
    private fun pauseRead() {
        isPause = true
        isRead = false
        //获取暂停朗读时间
        startTimeMillis2 = System.currentTimeMillis() / 1000
        mActivityScriptureDetailBinding.readTextview.text = "朗读"
        //暂停朗读
        tts.stop()
    }

    private fun initTextToSpeech() {
        tts = TextToSpeech(this, this)
        // 设置语速和音调
        tts.setSpeechRate(0.8f)  // 语速（0.5f-2.0f）
//        tts.setPitch(0.6f)      // 音调（0.5f-2.0f）男
        tts.setPitch(1.2f)      // 音调（0.5f-2.0f）女
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(p0: String?) {

            }

            override fun onDone(p0: String?) {
                isPause = false
                isRead = false
                ToastUtil.showShort(this@ScriptureDetailsActivity,"已朗读完成")
                // 可以在这里执行朗读完成后的操作，例如更新UI
                mActivityScriptureDetailBinding.readTextview.post {
                    mActivityScriptureDetailBinding.readTextview.text = "朗读"
                }
            }

            override fun onError(p0: String?) {}

            override fun onStop(utteranceId: String?, interrupted: Boolean) {
                super.onStop(utteranceId, interrupted)
            }
            override fun onBeginSynthesis(
                utteranceId: String?,
                sampleRateInHz: Int,
                audioFormat: Int,
                channelCount: Int
            ) {
                super.onBeginSynthesis(utteranceId, sampleRateInHz, audioFormat, channelCount)
            }
            override fun onAudioAvailable(utteranceId: String?, audio: ByteArray?) {
                super.onAudioAvailable(utteranceId, audio)
            }
            override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
                super.onRangeStart(utteranceId, start, end, frame)
            }
        })
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // 设置语言为中文（可根据需要修改）
            val result = tts.setLanguage(Locale.CHINA)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                ToastUtil.showShort(this@ScriptureDetailsActivity, "语言不支持")
                // 提示用户安装语言包
                val installIntent = Intent()
                installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(installIntent)
            } else {
                // 启用按钮
                mActivityScriptureDetailBinding.readTextview.isEnabled = true
            }
        } else {
            ToastUtil.showShort(this@ScriptureDetailsActivity, "语音朗读初始化失败")
        }
    }

    override fun onResume() {
        mActivityScriptureDetailBinding.readTextview.text = "朗读"
        //获取当前显示的文字
        mActivityScriptureDetailBinding.scriptureTextView.post {
            ttsText = TextUtil.getWindowText(mActivityScriptureDetailBinding.scriptureTextView,mActivityScriptureDetailBinding.scriptureScrollView)
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        tts.stop()
        isPause = true
        if(isRead){
            //获取暂停朗读时间
            startTimeMillis2 = System.currentTimeMillis() / 1000
        }

        mSharedPreferencesHelper.put(position.toString(),scrollY)
    }

    override fun onDestroy() {
        // 释放 TTS 资源
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        isPause = false
        isRead = false
        super.onDestroy()
    }
}