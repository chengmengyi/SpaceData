package com.demo.spacedata.bean

class Ad0906Res(
    val t:Long=0L,
    val res:Any?=null
) {
    fun expired()=(System.currentTimeMillis() - t) >=1000L*60L * 60L
}