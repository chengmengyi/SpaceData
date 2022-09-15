package com.demo.spacedata

import android.app.ActivityManager
import android.app.Application
import com.demo.spacedata.activity.Ac0906Connect
import com.demo.spacedata.helper.RegisterAcCallback
import com.demo.spacedata.server.Load0906ConfManager
import com.github.shadowsocks.Core
import com.tencent.mmkv.MMKV

lateinit var mSpace:Space
class Space:Application() {
    override fun onCreate() {
        super.onCreate()
        mSpace=this
        Core.init(this,Ac0906Connect::class)
        MMKV.initialize(this)
        if (!packageName.equals(processName(this))){
            return
        }
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