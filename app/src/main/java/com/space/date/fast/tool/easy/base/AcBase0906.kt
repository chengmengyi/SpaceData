package com.space.date.fast.tool.easy.base

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.space.date.fast.tool.easy.conf.Conf0906
import com.space.date.fast.tool.easy.helper.fitHeight
import com.gyf.immersionbar.ImmersionBar
import java.lang.Exception

abstract class AcBase0906(
    private val resId:Int
):AppCompatActivity() {
    protected lateinit var immer:ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fitHeight()
        setContentView(resId)
        immer= ImmersionBar.with(this).apply {
            statusBarAlpha(0f)
            autoDarkModeEnable(true)
            statusBarDarkFont(false)
            init()
        }
        viewCreated()
    }

    abstract fun viewCreated()

    fun getNetWorkStatus(): Int {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return 2
            } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return 0
            }
        } else {
            return 1
        }
        return 1
    }

    fun showToast(s: String){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }


    fun contact(){
        try {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data= Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, Conf0906.E)
            startActivity(intent)
        }catch (e: Exception){
            showToast("Contact us by email：${Conf0906.E}")
        }
    }

    fun update() {
        val packName = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).packageName
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://play.google.com/store/apps/details?id=$packName"
            )
        }
        startActivity(intent)
    }

    fun share() {
        val pm = packageManager
        val packageName=pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).packageName
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/apps/details?id=${packageName}"
        )
        startActivity(Intent.createChooser(intent, "share"))
    }
}