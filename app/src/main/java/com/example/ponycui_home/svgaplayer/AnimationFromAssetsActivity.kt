package com.example.ponycui_home.svgaplayer

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.BoringLayout
import android.text.Layout
import android.text.TextPaint
import android.util.Log
import com.example.ponycui_home.svgaplayer.databinding.ActivityFromAssetBinding
import com.opensource.svgaplayer.SVGADynamicEntity
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGAParser.Companion.shareParser
import com.opensource.svgaplayer.SVGAParser.ParseCompletion
import com.opensource.svgaplayer.SVGASoundManager.init
import com.opensource.svgaplayer.SVGAVideoEntity
import com.opensource.svgaplayer.utils.log.SVGALogger.setLogEnabled

class AnimationFromAssetsActivity : Activity() {
    var currentIndex = 0
    var animationView: SVGAImageView? = null
    private lateinit var binding: ActivityFromAssetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFromAssetBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)

        animationView = binding.svga2
//        animationView!!.setBackgroundColor(Color.BLACK)
        animationView!!.setOnClickListener { animationView!!.stepToFrame(currentIndex++, false) }
        setLogEnabled(true)
        init()
        loadAnimation()
        loadFloatVipImage("fx_privilege_xrtq.svga", 2323)
    }

    private var activityCountDown: CountDownTimer? = null
    private var activityCountDownDynamicEntity: SVGADynamicEntity? = null
    private var activityCountDownTextLayout: BoringLayout? = null
    private var activityCountDownTextPaint: TextPaint? = null
    private val metrics = BoringLayout.Metrics()

    private fun loadFloatVipImage(vipImgUrl: String, limitSeconds: Long) {
        if (vipImgUrl.endsWith(".svga")) {
            var textKey: String? = null
            if (limitSeconds > 0) {
                textKey = "wenzi_time"
                if (activityCountDownTextLayout == null) {
                    activityCountDownTextPaint = TextPaint()
                    activityCountDownTextPaint?.color = Color.WHITE
                    activityCountDownTextPaint?.textSize =
                        resources.displayMetrics.scaledDensity * 15f
                    activityCountDownTextPaint?.fontMetrics?.let {
                        metrics.ascent = it.ascent.toInt()
                        metrics.bottom = it.bottom.toInt()
                        metrics.top = it.top.toInt()
                        metrics.descent = it.descent.toInt()
                        metrics.leading = it.leading.toInt()
                    }
//                    activityCountDownTextPaint?.typeface =
//                        Typeface.createFromAsset(assets, "MS-Bold.ttf")
                    activityCountDownTextLayout = BoringLayout.make(
                        "",
                        activityCountDownTextPaint,
                        0,
                        Layout.Alignment.ALIGN_NORMAL,
                        1.0f,
                        0f,
                        metrics,
                        true
                    )
                }
                activityCountDown?.cancel()
                activityCountDown = object : CountDownTimer(limitSeconds * 1000L, 1000L) {
                    override fun onTick(millisUntilFinished: Long) {
                        activityCountDownTextLayout?.replaceOrMake(
                            "${(millisUntilFinished / 3600000)}:${((millisUntilFinished / 1000)%3600) / 60}:${(millisUntilFinished / 1000) % 60}",
                            activityCountDownTextPaint,
                            0,
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0f,
                            metrics,
                            true
                        )
                        activityCountDownDynamicEntity?.setDynamicText(
                            activityCountDownTextLayout!!,
                            textKey
                        )
                    }

                    override fun onFinish() {
                        activityCountDown?.cancel()
                        activityCountDown = null
                        activityCountDownDynamicEntity = null
                        activityCountDownTextLayout = null
                    }
                }
            }
            binding.svga1.loadFromUrlWithText(
                this,
                url = vipImgUrl,
                key = textKey,
                textLayout = activityCountDownTextLayout
            ) {
                activityCountDownDynamicEntity = it
                activityCountDown?.start()
            }
        } else {
        }

    }
    private fun loadAnimation() {
        val svgaParser = shareParser()
        //        String name = this.randomSample();
        //asset jojo_audio.svga  cannot callback
        val name = "mp3_to_long.svga"
        Log.d("SVGA", "## name $name")
        svgaParser.setFrameSize(100, 100)
        svgaParser.decodeFromAssets(name, object : ParseCompletion {
            override fun onComplete(videoItem: SVGAVideoEntity) {
                Log.e("zzzz", "onComplete: ")
                animationView!!.setVideoItem(videoItem)
                animationView!!.stepToFrame(0, true)
            }

            override fun onError() {
                Log.e("zzzz", "onComplete: ")
            }
        }, null)
    }

    private val samples: ArrayList<String?> = ArrayList()
    private fun randomSample(): String? {
        if (samples.size == 0) {
            samples.add("750x80.svga")
            samples.add("alarm.svga")
            samples.add("angel.svga")
            samples.add("Castle.svga")
            samples.add("EmptyState.svga")
            samples.add("Goddess.svga")
            samples.add("gradientBorder.svga")
            samples.add("heartbeat.svga")
            samples.add("matteBitmap.svga")
            samples.add("matteBitmap_1.x.svga")
            samples.add("matteRect.svga")
            samples.add("MerryChristmas.svga")
            samples.add("posche.svga")
            samples.add("Rocket.svga")
            samples.add("rose.svga")
            samples.add("rose_2.0.0.svga")
        }
        return samples[Math.floor(Math.random() * samples.size).toInt()]
    }
}