package com.space.date.fast.tool.easy.server

import com.space.date.fast.tool.easy.interfaces.IUpdateConnectTimeListener
import kotlinx.coroutines.*

object Time0906Manager {
    var time=0L
    private var timeJob:Job?=null
    private var iUpdateConnectTimeListener:IUpdateConnectTimeListener?=null

    fun start(){
        if (null!= timeJob) return
        timeJob = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                iUpdateConnectTimeListener?.updateConnectTime(time)
                time++
                delay(1000L)
            }
        }
    }

    fun stop(){
        timeJob?.cancel()
        timeJob=null
    }

    fun setIUpdateConnectTimeListener(iUpdateConnectTimeListener:IUpdateConnectTimeListener){
        this.iUpdateConnectTimeListener=iUpdateConnectTimeListener
    }

    fun removeIUpdateConnectTimeListener(){
        this.iUpdateConnectTimeListener=null
    }
}