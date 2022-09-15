package com.demo.spacedata.admob0906

import com.demo.spacedata.base.AcBase0906
import com.demo.spacedata.server.Load0906ConfManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Show0906OpenFullCallback(
    private val acBase0906: AcBase0906,
    private val adloca:String,
    private val next:()->Unit
    ): FullScreenContentCallback() {
    override fun onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent()
        Load0906ConfManager.showingOpen=false
        showNext()
    }

    override fun onAdShowedFullScreenContent() {
        super.onAdShowedFullScreenContent()
        Load0906ConfManager.showingOpen=true
        Load0906AdManager.removeAdRes(adloca)
    }

    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
        super.onAdFailedToShowFullScreenContent(p0)
        Load0906ConfManager.showingOpen=false
        Load0906AdManager.removeAdRes(adloca)
        showNext()
    }

    private fun showNext(){
        if (adloca==Ad0907Loca.CONNECT){
            Load0906AdManager.preLoad(adloca)
        }
        GlobalScope.launch(Dispatchers.Main) {
            delay(200L)
            if (acBase0906.resume){
                next.invoke()
            }
        }
    }
}