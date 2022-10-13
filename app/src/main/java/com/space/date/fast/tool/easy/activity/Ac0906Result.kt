package com.space.date.fast.tool.easy.activity

import com.space.date.fast.tool.easy.R
import com.space.date.fast.tool.easy.base.AcBase0906
import com.space.date.fast.tool.easy.helper.getFlagIcon
import com.space.date.fast.tool.easy.server.Connect0906Manager
import kotlinx.android.synthetic.main.ac_0906_result.*

class Ac0906Result:AcBase0906(R.layout.ac_0906_result) {
    override fun viewCreated() {
        immer.statusBarView(view).init()

        tv_result_connect_status.text=
            if (intent.getBooleanExtra("connect",false)) "Connected succeeded"
            else "Disconnected succeeded"

        iv_result_server_flag.setImageResource(getFlagIcon(Connect0906Manager.lastServerBean.country_space_0906))

        iv_back.setOnClickListener { finish() }
    }
}