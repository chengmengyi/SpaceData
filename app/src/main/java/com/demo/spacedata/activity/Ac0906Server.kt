package com.demo.spacedata.activity

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.spacedata.R
import com.demo.spacedata.adapter.Server0906Adapter
import com.demo.spacedata.base.AcBase0906
import com.demo.spacedata.bean.Server0906Bean
import com.demo.spacedata.server.Connect0906Manager
import kotlinx.android.synthetic.main.ac_0906_server.*

class Ac0906Server:AcBase0906(R.layout.ac_0906_server) {
    override fun viewCreated() {
        immer.statusBarView(view)?.init()

        val list= arrayListOf<Server0906Bean>()
        list.add(Connect0906Manager.createFastServerBean())
        list.addAll(Connect0906Manager.getServerList())

        rv.apply {
            layoutManager=LinearLayoutManager(this@Ac0906Server)
            adapter=Server0906Adapter(this@Ac0906Server,list){
                clickServer(it)
            }
        }

        iv_back.setOnClickListener { finish() }
    }

    private fun clickServer(server0906Bean: Server0906Bean){
        val connectOrDis = getConnectOrDis(server0906Bean)
        if (connectOrDis=="1"){
            showTipsDialog(connectOrDis,server0906Bean)
        }else{
            setResultAndFinish(connectOrDis,server0906Bean)
        }
    }

    private fun showTipsDialog(connectOrDis: String, server0906Bean: Server0906Bean) {
        AlertDialog.Builder(this).apply {
            setMessage("You are currently connected and need to disconnect before manually connecting to the server.")
            setPositiveButton("sure") { _, _ ->
                setResultAndFinish(connectOrDis,server0906Bean)
            }
            setNegativeButton("cancel",null)
            show()
        }
    }

    private fun getConnectOrDis(server0906Bean: Server0906Bean):String{
        var status=""
        val connected = Connect0906Manager.connected()
        if (Connect0906Manager.currentServerBean.host_space_0906==server0906Bean.host_space_0906){
            if (!connected){
                status="0"
            }
        }else{
            status=if (connected) "1" else "0"
        }
        return status
    }

    private fun setResultAndFinish(status:String,server0906Bean: Server0906Bean){
        Connect0906Manager.currentServerBean=server0906Bean
        setResult(906,Intent().apply {
            putExtra("status",status)
        })
        finish()
    }
}