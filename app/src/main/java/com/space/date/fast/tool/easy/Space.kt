package com.space.date.fast.tool.easy

import android.app.ActivityManager
import android.app.Application
import com.space.date.fast.tool.easy.activity.Ac0906Connect
import com.space.date.fast.tool.easy.helper.RegisterAcCallback
import com.space.date.fast.tool.easy.server.Load0906ConfManager
import com.github.shadowsocks.Core
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.tencent.mmkv.MMKV

class Space:Application() {
    override fun onCreate() {
        super.onCreate()
        Core.init(this,Ac0906Connect::class)
        MMKV.initialize(this)
        if (!packageName.equals(processName(this))){
            return
        }
        Firebase.initialize(this)
        RegisterAcCallback.register(this)
        Load0906ConfManager.loadConf()
    }
    private fun processName(applicationContext: Application): String {
        val pid = android.os.Process.myPid()
        var processName = ""
        val manager = applicationContext.getSystemService(Application.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid === pid) {
                processName = process.processName
            }
        }
        return processName
    }
}