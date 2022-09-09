package com.demo.spacedata.server

import com.demo.spacedata.bean.Server0906Bean
import com.demo.spacedata.conf.Conf0906
import com.github.shadowsocks.database.Profile
import com.github.shadowsocks.database.ProfileManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Load0906ConfManager {

    fun loadConf(){
        saveStatusJson(Conf0906.JSON)
        createServerId(Conf0906.localServer)


//        val remoteConfig = Firebase.remoteConfig
//        remoteConfig.fetchAndActivate().addOnCompleteListener {
//            if (it.isSuccessful){
//                saveStatusJson(remoteConfig.getString("space_0906_status"))
//            }
//        }
    }

    private fun saveStatusJson(string: String){
        MMKV.mmkvWithID("space").encode("status",string)
    }

    private fun createServerId(serverList:ArrayList<Server0906Bean>){
        GlobalScope.launch {
            serverList.forEach {
              create(it)
            }
        }
    }

    private fun create(server0906Bean: Server0906Bean){
        val profile = Profile(
            id = 0L,
            name = "${server0906Bean.country_space_0906} - ${server0906Bean.city_space_0906}",
            host = server0906Bean.host_space_0906,
            remotePort = server0906Bean.port_space_0906,
            password = server0906Bean.pwd_space_0906,
            method = server0906Bean.method_space_0906
        )

        var id:Long?=null
        ProfileManager.getActiveProfiles()?.forEach {
            if (it.remotePort==profile.remotePort&&it.host==profile.host){
                id=it.id
                return@forEach
            }
        }
        if (null==id){
            ProfileManager.createProfile(profile)
        }else{
            profile.id=id!!
            ProfileManager.updateProfile(profile)
        }
    }
}