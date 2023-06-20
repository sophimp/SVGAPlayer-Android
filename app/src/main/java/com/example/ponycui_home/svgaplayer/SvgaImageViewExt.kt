package com.example.ponycui_home.svgaplayer

import android.content.Context
import android.text.BoringLayout
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGADynamicEntity
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import com.opensource.svgaplayer.utils.SVGARange

/**
 * create by sfx on 2023/5/19 10:27
 */

/**
 * 加载小序IPView
 * @param manager 传值的话，默认点击事件弹窗 AIBottomDialogFragment
 */
fun SVGAImageView.loadFromUrlWithText(
    context: Context,
    url: String,
    svgaRange: SVGARange? = null,
    textLayout: BoringLayout? = null,
    key: String? = null,
    onDynamicText: (dynamicText: SVGADynamicEntity?) -> Unit
) {
    SVGAParser(context).decodeFromAssets(
        url,
//        URL(url),
        object : SVGAParser.ParseCompletion {
            override fun onComplete(videoItem: SVGAVideoEntity) {
                var drawable: SVGADrawable
                if (key.isNullOrEmpty()) {
                    drawable = SVGADrawable(videoItem)
                } else {
                    val dynamicEntity = SVGADynamicEntity()
                    textLayout?.let {
                        dynamicEntity.setDynamicText(it, key)
                    }

                    drawable = SVGADrawable(videoItem, dynamicEntity)
                    onDynamicText.invoke(dynamicEntity)
                }
                setImageDrawable(drawable)
                startAnimation(svgaRange, false)
            }

            override fun onError() {
            }

        }, null
    )
}

fun SVGAImageView.loadFromAssets(context: Context, assets: String, svgaRange: SVGARange? = null) {
    SVGAParser(context).decodeFromAssets(assets,
        object : SVGAParser.ParseCompletion {
            override fun onComplete(videoItem: SVGAVideoEntity) {
                val drawable = SVGADrawable(videoItem)
                setImageDrawable(drawable)
                startAnimation(svgaRange, false)
            }

            override fun onError() {
            }

        })
}
