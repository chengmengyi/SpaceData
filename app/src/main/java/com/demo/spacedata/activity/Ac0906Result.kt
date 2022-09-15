package com.demo.spacedata.activity

import com.demo.spacedata.R
import com.demo.spacedata.admob0906.Ad0907Loca
import com.demo.spacedata.admob0906.Show0906Home
import com.demo.spacedata.base.AcBase0906
import com.demo.spacedata.helper.getFlagIcon
import com.demo.spacedata.server.Connect0906Manager
import kotlinx.android.synthetic.main.ac_0906_result.*

class Ac0906Result:AcBase0906(R.layout.ac_0906_result) {
    private val showhome by lazy { Show0906Home(this, Ad0907Loca.RESULT) }


    override fun viewCreated() {
        immer.statusBarView(view).init()

        tv_result_connect_status.text=
            if (intent.getBooleanExtra("connect",false)) "Connected succeeded"
            else "Disconnected succeeded"

        iv_result_server_flag.setImageResource(getFlagIcon(Connect0906Manager.lastServerBean.country_space_0906))

        iv_back.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        showhome.call()
    }

    override fun onDestroy() {
        super.onDestroy()
        showhome.cancelJob()
    }
}