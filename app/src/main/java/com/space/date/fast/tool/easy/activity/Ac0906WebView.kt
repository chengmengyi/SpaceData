package com.space.date.fast.tool.easy.activity

import com.space.date.fast.tool.easy.R
import com.space.date.fast.tool.easy.base.AcBase0906
import com.space.date.fast.tool.easy.conf.Conf0906
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