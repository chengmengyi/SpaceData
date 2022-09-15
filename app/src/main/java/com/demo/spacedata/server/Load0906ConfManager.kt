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
import org.json.JSONObject

object Load0906ConfManager {

    var showingOpen=false

    val cList= arrayListOf<String>()
    val sList= arrayListOf<Server0906Bean>()

    fun loadConf(){
        saveStatusJson(Conf0906.JSON)
        createServerId(Conf0906.localServer)
        


//        val remoteConfig = Firebase.remoteConfig
//        remoteConfig.fetchAndActivate().addOnCompleteListener {
//            if (it.isSuccessful){
//                saveStatusJson(remoteConfig.getString("space_0906_status"))
//                loadCList(remoteConfig.getString("space0906_city"))
//                loadSList(remoteConfig.getString("space0906_server"))
//                loadAdStr(remoteConfig.getString("space0906_ad"))
//            }
//        }
    }

    private fun loadCList(s:String){
        try {
            cList.clear()
            val jsonArray = JSONObject(s).getJSONArray("space0906_city")
            for (index in 0 until jsonArray.length()){
                cList.add(jsonArray.optString(index))
            }
        }catch (e:Exception){

        }
    }
    
    private fun loadSList(s:String){
        sList.clear()
        try {
            val jsonArray = JSONObject(s).getJSONArray("space0906_server")
            for (index in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(index)
                val server = Server0906Bean(
                    jsonObject.optString("host_space0906"),
                    jsonObject.optInt("port_space0906"),
                    jsonObject.optString("pwd_space0906"),
                    jsonObject.optString("country_space0906"),
                    jsonObject.optString("city_space0906"),
                    jsonObject.optString("method_space0906"),
                )
                sList.add(server)
            }
            createServerId(sList)
        }catch (e:Exception){

        }
    }

    private fun loadAdStr(s: String){
        MMKV.defaultMMKV().encode("space0906_ad",s)
    }
    
    fun getAdStr():String{
        val ad = MMKV.defaultMMKV().decodeString("space0906_ad")
        return if (ad.isNullOrEmpty()) Conf0906.AD_0906 else ad
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