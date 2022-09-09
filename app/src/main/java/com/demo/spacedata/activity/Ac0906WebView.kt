package com.demo.spacedata.activity

import com.demo.spacedata.R
import com.demo.spacedata.base.AcBase0906
import com.demo.spacedata.conf.Conf0906
import kotlinx.android.synthetic.main.ac_0906_webview.*

class Ac0906WebView :AcBase0906(R.layout.ac_0906_webview){
    override fun viewCreated() {
        immer.statusBarView(view).init()

        web.apply {
            settings.javaScriptEnabled=true
            loadUrl(Conf0906.U)
        }

        iv_back.setOnClickListener { finish() }
    }
}