package com.demo.spacedata.server

import com.demo.spacedata.bean.Server0906Bean
import com.demo.spacedata.conf.Conf0906
import com.github.shadowsocks.aidl.ShadowsocksConnection
import com.github.shadowsocks.bg.BaseService
import com.github.shadowsocks.database.ProfileManager

object Connect0906Manager {
    var click=true
    var hasPer=false
    var isConnect=true
    val sc= ShadowsocksConnection(true)
    var state=BaseService.State.Idle
    var currentServerBean:Server0906Bean= createFastServerBean()
    var lastServerBean:Server0906Bean= createFastServerBean()

    fun updateLastServer(){
        lastServerBean= currentServerBean
    }

    fun getServerList()= Load0906ConfManager.sList.ifEmpty { Conf0906.localServer }

    fun getFastServerBean():Server0906Bean{
        val serverList = getServerList()
        if (!Load0906ConfManager.cList.isNullOrEmpty()){
            val filter = serverList.filter { Load0906ConfManager.cList.contains(it.city_space_0906) }
            if (!filter.isNullOrEmpty()){
                return filter.random()
            }
        }
        return serverList.random()
    }

    fun createFastServerBean() = Server0906Bean(country_space_0906 = "Faster server")

    fun connected()= state==BaseService.State.Connected

    fun stopped()= state==BaseService.State.Stopped

    fun isFastServerBean(server0906Bean: Server0906Bean)=server0906Bean.host_space_0906.isEmpty()&&server0906Bean.country_space_0906=="Faster server"

    fun getServerId(server0906Bean: Server0906Bean):Long{
        ProfileManager.getActiveProfiles()?.forEach {
            if (it.host==server0906Bean.host_space_0906&&it.remotePort==server0906Bean.port_space_0906){
                return it.id
            }
        }
        return 0L
    }
}