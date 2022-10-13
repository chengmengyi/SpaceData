package com.space.date.fast.tool.easy.helper

import android.content.Context
import android.util.DisplayMetrics
import com.space.date.fast.tool.easy.R
import com.space.date.fast.tool.easy.bean.Server0906Bean
import com.space.date.fast.tool.easy.server.Connect0906Manager
import java.lang.Exception

fun Context.fitHeight(){
    val metrics: DisplayMetrics = resources.displayMetrics
    val td = metrics.heightPixels / 760f
    val dpi = (160 * td).toInt()
    metrics.density = td
    metrics.scaledDensity = td
    metrics.densityDpi = dpi
}

fun transTime(t:Long):String{
    try {
        val shi=t/3600
        val fen= (t % 3600) / 60
        val miao= (t % 3600) % 60
        val s=if (shi<10) "0${shi}" else shi
        val f=if (fen<10) "0${fen}" else fen
        val m=if (miao<10) "0${miao}" else miao
        return "${s}:${f}:${m}"
    }catch (e: Exception){}
    return "00:00:00"
}

fun getFlagIcon(string:String)=when(string){
    "Australia"-> R.drawable.australia
    "Japan"-> R.drawable.japan
    else -> R.drawable.fast
}

fun getFlagName(server0906Bean: Server0906Bean):String{
    return if (Connect0906Manager.isFastServerBean(server0906Bean)){
        "Faster server"
    }else{
        "${server0906Bean.country_space_0906} - ${server0906Bean.city_space_0906}"
    }
}
