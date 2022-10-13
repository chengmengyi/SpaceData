package com.space.date.fast.tool.easy.activity

import android.content.Intent
import com.space.date.fast.tool.easy.R
import com.space.date.fast.tool.easy.base.AcBase0906
import kotlinx.android.synthetic.main.ac_0906_set.*

class Ac0906Set:AcBase0906(R.layout.ac_0906_set) {
    override fun viewCreated() {
        immer.statusBarView(view).init()

        iv_back.setOnClickListener { finish() }

        ll_contact.setOnClickListener {
            contact()
        }

        ll_agreement.setOnClickListener {
            startActivity(Intent(this,Ac0906WebView::class.java))
        }
        ll_update.setOnClickListener {
            update()
        }

        ll_share.setOnClickListener {
            share()
        }
    }
}