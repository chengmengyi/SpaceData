package com.space.date.fast.tool.easy.helper

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.space.date.fast.tool.easy.activity.Ac0906Connect
import com.space.date.fast.tool.easy.activity.Ac0906Launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object RegisterAcCallback {
    private var delayJob: Job?=null
    private var pass3s=false
    var refreshHomeNativeAd=true
    var front0906=true


    fun register(application: Application){
        application.registerActivityLifecycleCallbacks(callback)
    }

    private val callback=object : Application.ActivityLifecycleCallbacks{
        private var num=0
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {
            num++
            delayJob?.cancel()
            delayJob=null
            if (num==1){
                front0906=true
                if (pass3s){
                    refreshHomeNativeAd=true
                    if (ActivityUtils.isActivityExistsInStack(Ac0906Connect::class.java)){
                        activity.startActivity(Intent(activity, Ac0906Launch::class.java))
                    }
                }
                pass3s=false
            }
        }

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {
            num--
            if (num<=0){
                front0906=false
                delayJob= GlobalScope.launch {
                    delay(3000L)
                    pass3s=true
                    ActivityUtils.finishActivity(Ac0906Launch::class.java)
//                    ActivityUtils.finishActivity(AdActivity::class.java)
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}
    }
}