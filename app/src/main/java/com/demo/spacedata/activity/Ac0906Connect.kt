package com.demo.spacedata.activity

import android.animation.ValueAnimator
import android.content.Intent
import android.net.VpnService
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AlertDialog
import com.demo.spacedata.R
import com.demo.spacedata.base.AcBase0906
import com.demo.spacedata.helper.RegisterAcCallback
import com.demo.spacedata.helper.getFlagIcon
import com.demo.spacedata.helper.getFlagName
import com.demo.spacedata.helper.transTime
import com.demo.spacedata.interfaces.IUpdateConnectTimeListener
import com.demo.spacedata.server.Connect0906Manager
import com.demo.spacedata.server.Time0906Manager
import com.github.shadowsocks.Core
import com.github.shadowsocks.aidl.IShadowsocksService
import com.github.shadowsocks.aidl.ShadowsocksConnection
import com.github.shadowsocks.bg.BaseService
import com.github.shadowsocks.preference.DataStore
import com.github.shadowsocks.utils.StartService
import kotlinx.android.synthetic.main.ac_0906_connect.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Ac0906Connect :AcBase0906(R.layout.ac_0906_connect),ShadowsocksConnection.Callback,IUpdateConnectTimeListener{
    private var loopCheckAnimator:ValueAnimator?=null

    private val registerForActivityResult=registerForActivityResult(StartService()) {
        if (!it && Connect0906Manager.hasPer) {
            Connect0906Manager.hasPer = false
            doConnectLogic()
        } else {
            Connect0906Manager.click=true
            showToast("Connected fail")
        }
    }

    override fun viewCreated() {
        immer.statusBarView(view).init()
        Connect0906Manager.click=true
        Time0906Manager.setIUpdateConnectTimeListener(this)
        Connect0906Manager.sc.connect(this,this)
        updateServerInfo()

        iv_connect_center.setOnClickListener {
            clickConnectBtn()
        }

        iv_server.setOnClickListener {
            if (Connect0906Manager.click){
                startActivityForResult(Intent(this,Ac0906Server::class.java),906)
            }
        }
        iv_set.setOnClickListener {
            if (Connect0906Manager.click){
                startActivity(Intent(this,Ac0906Set::class.java))
            }
        }
    }

    private fun clickConnectBtn(){
        if (Connect0906Manager.click){
            Connect0906Manager.click=false
            val connected = Connect0906Manager.connected()
            if (connected){
                doDisconnectLogic()
            }else{
                if (getNetWorkStatus()==1){
                    AlertDialog.Builder(this).apply {
                        setMessage("You are not currently connected to the network")
                        setPositiveButton("sure", null)
                        show()
                    }
                    Connect0906Manager.click=true
                    return
                }
                if (VpnService.prepare(this) != null) {
                    Connect0906Manager.hasPer = true
                    registerForActivityResult.launch(null)
                    return
                }
                doConnectLogic()
            }
        }
    }

    private fun doConnectLogic(){
        updateServerInfo()
        Connect0906Manager.state=BaseService.State.Connecting
        GlobalScope.launch {
            if (Connect0906Manager.isFastServerBean(Connect0906Manager.currentServerBean)){
                DataStore.profileId = Connect0906Manager.getServerId(Connect0906Manager.getFastServerBean())
            }else{
                DataStore.profileId = Connect0906Manager.getServerId(Connect0906Manager.currentServerBean)
            }
            Core.startService()
        }
        Time0906Manager.time=0L
        setConnectText("Connecting...")
        loopCheckConnectResult(true)
    }

    private fun doDisconnectLogic(){
        setConnectText("Stopping...")
        Connect0906Manager.state=BaseService.State.Stopping
        GlobalScope.launch {
            Core.stopService()
        }
        loopCheckConnectResult(false)
    }

    private fun loopCheckConnectResult(connect:Boolean){
        Connect0906Manager.isConnect=connect
        loopCheckAnimator = ValueAnimator.ofInt(0, 100).apply {
            duration=10000L
            interpolator = LinearInterpolator()
            addUpdateListener {
                val p = it.animatedValue as Int
                val i = if (connect) p else 100 - p
                connect_progress.progress=i
                val duration = (10 * (p / 100.0F)).toInt()
                if (duration in 2..9){
                    if (loopCheckSuccess()){
                        connect_progress.progress=if (connect) 100 else 0
                        stopLoopCheck()
                        loopCheckCompleted()
                    }
                }else if (duration>=10){
                    loopCheckCompleted()
                }
            }
            start()
        }
    }

    private fun loopCheckCompleted(){
        if (loopCheckSuccess()){
            if (Connect0906Manager.isConnect){
                updateConnectedUI()
            }else{
                updateStoppedUI()
                updateServerInfo()
                updateConnectTime(0L)
            }
            if (RegisterAcCallback.front0906){
                startActivity(Intent(this,Ac0906Result::class.java).apply {
                    putExtra("connect",Connect0906Manager.isConnect)
                })
            }
            Connect0906Manager.click=true
        }else{
            showToast(if (Connect0906Manager.isConnect) "Connect Fail" else "Disconnect Fail")
            updateConnectTime(0L)
            updateStoppedUI()
            Connect0906Manager.click=true
        }
    }

    private fun stopLoopCheck(){
        loopCheckAnimator?.removeAllUpdateListeners()
        loopCheckAnimator?.cancel()
        loopCheckAnimator=null
    }

    private fun loopCheckSuccess() = if (Connect0906Manager.isConnect) Connect0906Manager.connected() else Connect0906Manager.stopped()

    private fun updateServerInfo(){
        val currentServerBean = Connect0906Manager.currentServerBean
        tv_connect_server_name.text= getFlagName(currentServerBean)
        iv_connect_server_flag.setImageResource(getFlagIcon(currentServerBean.country_space_0906))
    }

    override fun stateChanged(state: BaseService.State, profileName: String?, msg: String?) {
        Connect0906Manager.state=state
        if (Connect0906Manager.stopped()){
            Time0906Manager.stop()
            if (Connect0906Manager.click){
                updateStoppedUI()
            }
        }

        if (Connect0906Manager.connected()){
            Time0906Manager.start()
            Connect0906Manager.updateLastServer()
        }
    }

    override fun onServiceConnected(service: IShadowsocksService) {
        val state = BaseService.State.values()[service.state]
        Connect0906Manager.state=state
        if (Connect0906Manager.connected()){
            Time0906Manager.start()
            updateConnectedUI()
            Connect0906Manager.updateLastServer()
        }
    }

    private fun updateConnectedUI(){
        setConnectText("Connected")
        connect_progress.progress=100
    }

    private fun updateStoppedUI(){
        setConnectText("Connect")
        connect_progress.progress=0
    }

    override fun onBinderDied() {
        Connect0906Manager.sc.disconnect(this)
    }

    private fun setConnectText(text:String){
        tv_connect_status.text=text
    }

    override fun updateConnectTime(time: Long) {
        tv_connect_time.text= transTime(time)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==906){
            when(data?.getStringExtra("status")){
                "0","1"->{

                    clickConnectBtn()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onBinderDied()
        stopLoopCheck()
        Time0906Manager.removeIUpdateConnectTimeListener()
    }
}