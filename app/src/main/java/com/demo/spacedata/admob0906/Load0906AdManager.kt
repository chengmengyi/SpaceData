package com.demo.spacedata.admob0906

import com.demo.spacedata.bean.Ad0906Res
import com.demo.spacedata.bean.Admob0906Bean
import com.demo.spacedata.helper.logDog
import com.demo.spacedata.mSpace
import com.demo.spacedata.server.Load0906ConfManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAdOptions
import org.json.JSONObject

object Load0906AdManager {
    private val loading= arrayListOf<String>()
    private val admob0906Res= hashMapOf<String,Ad0906Res>()

    fun preLoad(adLoca:String,again:Boolean=true){
        if (loading.contains(adLoca)){
            logDog("$adLoca loading")
            return
        }

        if (admob0906Res.containsKey(adLoca)){
            val ad0906Res = admob0906Res[adLoca]
            if (ad0906Res?.res!=null){
                if (ad0906Res.expired()){
                    removeAdRes(adLoca)
                }else{
                    logDog("$adLoca cache")
                    return
                }
            }
        }

        val ad0907List = getAd0907List(adLoca)
        if (ad0907List.isNullOrEmpty()){
            logDog("$adLoca list empty")
            return
        }
        loading.add(adLoca)
        loop(adLoca,ad0907List.iterator(),again)
    }

    private fun loop(adLoca: String, ite: Iterator<Admob0906Bean>,again:Boolean=true){
        val next = ite.next()
        loadAd(adLoca,next){ success,data->
            if (success){
                loading.remove(adLoca)
                if (data.res!=null){
                    admob0906Res[adLoca]=data
                }
            }else{
                if (ite.hasNext()){
                    loop(adLoca,ite,again)
                }else{
                    loading.remove(adLoca)
                    if (again&&adLoca==Ad0907Loca.OPEN){
                        preLoad(adLoca,again = false)
                    }
                }
            }
        }
    }

    private fun loadAd(adLoca:String,adInfo:Admob0906Bean,result:(success:Boolean,Ad0906Res)->Unit) {
        logDog("load ad,${adInfo.toString()}")
        when(adInfo.type_space0906){
            "kping"-> {
                loadKPingAd(adLoca,adInfo.id_space0906,result)
            }
            "cping"->{
                loadCPingAd(adLoca,adInfo.id_space0906,result)
            }
            "ysheng"->{
                loadYShengAd(adLoca,adInfo.id_space0906,result)
            }
        }
    }

    private fun loadKPingAd(adLoca: String,id:String,result: (success: Boolean, Ad0906Res) -> Unit){
        AppOpenAd.load(
            mSpace,
            id,
            AdRequest.Builder().build(),
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback(){
                override fun onAdLoaded(p0: AppOpenAd) {
                    super.onAdLoaded(p0)
                    logDog("load ad success, $adLoca")
                    result.invoke(true, Ad0906Res(res = p0, t = System.currentTimeMillis()))
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    logDog("load ad fail, $adLoca,${p0.message}")
                    result.invoke(false,Ad0906Res())
                }
            }
        )
    }

    private fun loadCPingAd(adLoca: String,id:String,result: (success: Boolean, Ad0906Res) -> Unit){
        InterstitialAd.load(
            mSpace,
            id,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    logDog("load ad fail, $adLoca,${p0.message}")
                    result.invoke(false,Ad0906Res())
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    logDog("load ad success, $adLoca")
                    result.invoke(true, Ad0906Res(res = p0, t = System.currentTimeMillis()))
                }
            }
        )
    }

    private fun loadYShengAd(adLoca: String,id:String,result: (success: Boolean, Ad0906Res) -> Unit){
        AdLoader.Builder(
            mSpace,
            id
        ).forNativeAd {
            logDog("load ad success, $adLoca")
            result.invoke(true, Ad0906Res(res = it, t = System.currentTimeMillis()))
        }
            .withAdListener(object : AdListener(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    logDog("load ad fail, $adLoca,${p0.message}")
                    result.invoke(false,Ad0906Res())
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .setAdChoicesPlacement(
                        NativeAdOptions.ADCHOICES_TOP_LEFT
                    )
                    .build()
            )
            .build()
            .loadAd(AdRequest.Builder().build())
    }

    private fun getAd0907List(adLoca: String):List<Admob0906Bean>{
        val list= arrayListOf<Admob0906Bean>()
        try {
            val jsonArray = JSONObject(Load0906ConfManager.getAdStr()).getJSONArray(adLoca)
            for (index in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(index)
                val admobInfo0826Be = Admob0906Bean(
                    jsonObject.optString("space_0906_id"),
                    jsonObject.optInt("space_0906_sort"),
                    jsonObject.optString("space_0906_type"),
                    jsonObject.optString("space_0906_source"),
                )
                list.add(admobInfo0826Be)
            }
        }catch (e:Exception){}
        return list.filter { it.source_space0906 == "admob" }.sortedByDescending { it.sort_space0906 }
    }

    fun getAdRes(adLoca: String)= admob0906Res[adLoca]?.res

    fun removeAdRes(adLoca: String){
        admob0906Res.remove(adLoca)
    }
}