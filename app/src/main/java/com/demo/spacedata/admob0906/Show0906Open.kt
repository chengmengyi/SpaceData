package com.demo.spacedata.admob0906

import com.demo.spacedata.base.AcBase0906
import com.demo.spacedata.helper.logDog
import com.demo.spacedata.server.Load0906ConfManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Show0906Open(
    private val acBase0906: AcBase0906,
    private val adLoca:String,
    private val completed:()->Unit
) {

    fun show(finish:(to:Boolean)->Unit){
        val adRes = Load0906AdManager.getAdRes(adLoca)
        if (adRes==null&&Load0906ConfManager.is0906AdLimit()){
            finish.invoke(true)
            return
        }
        if (adRes!=null){
            if (cannotShow()){
                finish.invoke(false)
                return
            }
            logDog("show $adLoca ad")
            finish.invoke(false)
            Load0906ConfManager.showingOpen=true
            if (adRes is AppOpenAd){
                adRes.fullScreenContentCallback=Show0906OpenFullCallback(acBase0906, adLoca, completed)
                adRes.show(acBase0906)
            }
            if (adRes is InterstitialAd){
                adRes.fullScreenContentCallback=Show0906OpenFullCallback(acBase0906, adLoca, completed)
                adRes.show(acBase0906)
            }
        }
    }

    private fun cannotShow()=Load0906ConfManager.showingOpen||!acBase0906.resume

}