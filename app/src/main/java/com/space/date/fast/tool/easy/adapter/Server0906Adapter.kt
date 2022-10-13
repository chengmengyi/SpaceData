package com.space.date.fast.tool.easy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.space.date.fast.tool.easy.R
import com.space.date.fast.tool.easy.bean.Server0906Bean
import com.space.date.fast.tool.easy.helper.getFlagIcon
import com.space.date.fast.tool.easy.helper.getFlagName
import com.space.date.fast.tool.easy.server.Connect0906Manager
import kotlinx.android.synthetic.main.layout_0906_server_item.view.*

class Server0906Adapter (
    private val context:Context,
    private val list:ArrayList<Server0906Bean>,
    private val clickItem:(server0906Bean:Server0906Bean)->Unit
):RecyclerView.Adapter<Server0906Adapter.Server0906View>() {
    inner class Server0906View(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                clickItem.invoke(list[layoutPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Server0906View =
        Server0906View(LayoutInflater.from(context).inflate(R.layout.layout_0906_server_item,parent,false))

    override fun onBindViewHolder(holder: Server0906View, position: Int) {
        with(holder.itemView){
            val server0906Bean = list[position]
            iv_item_server_name.text= getFlagName(server0906Bean)
            iv_item_server_flag.setImageResource(getFlagIcon(server0906Bean.country_space_0906))
            iv_item_server_select.isSelected=server0906Bean.host_space_0906==Connect0906Manager.currentServerBean.host_space_0906
        }
    }

    override fun getItemCount(): Int = list.size
}