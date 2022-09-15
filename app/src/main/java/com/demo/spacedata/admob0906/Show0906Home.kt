package com.demo.spacedata.admob0906

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.blankj.utilcode.util.SizeUtils
import com.demo.spacedata.R
import com.demo.spacedata.base.AcBase0906
import com.demo.spacedata.helper.RegisterAcCallback
import com.demo.spacedata.helper.logDog
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import kotlinx.coroutines.*

class Show0906Home(
    private val acBase0906: AcBase0906,
    private val adloca:String
) {
    private var job0906:Job?=null
    private var ad0906Res:NativeAd?=null

    fun call(){
        Load0906AdManager.preLoad(adloca)
        if (job0906!=null){
            cancelJob()
        }
        job0906= GlobalScope.launch(Dispatchers.Main) {
            delay(300L)
            if (acBase0906.resume){
                while (true){
                    if (!isActive) {
                        break
                    }
                    val adRes = Load0906AdManager.getAdRes(adloca)
                    if (acBase0906.resume&&null!=adRes&&adRes is NativeAd){
                        cancel()
                        if (ad0906Res!=null){
                            ad0906Res!!.destroy()
                        }
                        ad0906Res=adRes
                        showHomeAd(adRes)
                    }
                    delay(1000L)
                }
            }
        }
    }

    private fun showHomeAd(ad: NativeAd){
        logDog("show native ad  $adloca")
        val native_ad_view=acBase0906.findViewById<NativeAdView>(R.id.native_ad_view)
        native_ad_view.mediaView=acBase0906.findViewById(R.id.native_ad_cover)
        if (null!=ad.mediaContent){
            native_ad_view.mediaView?.apply {
                setMediaContent(ad.mediaContent)
                setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        if (view == null || outline == null) return
                        outline.setRoundRect(
                            0,
                            0,
                            view.width,
                            view.height,
                            SizeUtils.dp2px(10F).toFloat()
                        )
                        view.clipToOutline = true
                    }
                }
            }
        }
        native_ad_view.headlineView=acBase0906.findViewById(R.id.native_ad_title)
        (native_ad_view.headlineView as AppCompatTextView).text=ad.headline

        native_ad_view.bodyView=acBase0906.findViewById(R.id.native_ad_desc)
        (native_ad_view.bodyView as AppCompatTextView).text=ad.body

        native_ad_view.iconView=acBase0906.findViewById(R.id.native_ad_logo)
        (native_ad_view.iconView as ImageFilterView).setImageDrawable(ad.icon?.drawable)

        native_ad_view.callToActionView=acBase0906.findViewById(R.id.native_ad_btn)
        (native_ad_view.callToActionView as AppCompatTextView).text=ad.callToAction

        native_ad_view.setNativeAd(ad)

        acBase0906.findViewById<AppCompatImageView>(R.id.home_ad_cover).visibility=View.GONE
        if (adloca==Ad0907Loca.HOME){
            RegisterAcCallback.refreshHomeNativeAd=false
        }
        Load0906AdManager.removeAdRes(adloca)
        Load0906AdManager.preLoad(adloca)
    }

    fun cancelJob(){
        job0906?.cancel()
        job0906=null
    }
}