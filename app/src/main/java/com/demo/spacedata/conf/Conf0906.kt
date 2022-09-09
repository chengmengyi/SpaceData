package com.demo.spacedata.conf

import com.demo.spacedata.bean.Server0906Bean

class Conf0906 {
    companion object{
        const val E=""
        const val U=""

        const val JSON="""{
    "status":2,
    "pack":[
        "com.UCMobile"
    ]
}"""

        val localServer= arrayListOf(
            Server0906Bean(
                host_space_0906 = "100.223.52.0",
                port_space_0906 = 100,
                pwd_space_0906 = "123456",
                country_space_0906 = "Japan",
                city_space_0906 = "Tokyo",
                method_space_0906 = "chacha20-ietf-poly1305",
            ),
            Server0906Bean(
                host_space_0906 = "100.223.52.78",
                port_space_0906 = 100,
                pwd_space_0906 = "123456",
                country_space_0906 = "Japan",
                city_space_0906 = "Tokyo",
                method_space_0906 = "chacha20-ietf-poly1305",
            ),
        )

    }
}