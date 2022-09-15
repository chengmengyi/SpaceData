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


        const val AD_0906="""{
    "space_0906_open": [
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/3419835294",
            "space_0906_type": "cping",
            "space_0906_sort": 1
        },
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/3419835294A",
            "space_0906_type": "kping",
            "space_0906_sort": 2
        },
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/3419835294AA",
            "space_0906_type": "kping",
            "space_0906_sort": 3
        }
    ],
    "space_0906_home": [
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/2247696110",
            "space_0906_type": "ysheng",
            "space_0906_sort": 2
        },
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/2247696110AAA",
            "space_0906_type": "ysheng",
            "space_0906_sort": 1
        },
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/2247696110AA",
            "space_0906_type": "ysheng",
            "space_0906_sort": 3
        }
    ],
    "space_0906_result": [
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/2247696110A",
            "space_0906_type": "ysheng",
            "space_0906_sort": 2
        },
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/2247696110",
            "space_0906_type": "ysheng",
            "space_0906_sort": 1
        },
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/2247696110AA",
            "space_0906_type": "ysheng",
            "space_0906_sort": 3
        }
    ],
    "space_0906_connect": [
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/8691691433A",
            "space_0906_type": "cping",
            "space_0906_sort": 2
        },
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/1033173712",
            "space_0906_type": "cping",
            "space_0906_sort": 1
        },
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/1033173712AA",
            "space_0906_type": "cping",
            "space_0906_sort": 3
        }
    ],
    "space_0906_back": [
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/1033173712A",
            "space_0906_type": "cping",
            "space_0906_sort": 2
        },
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/8691691433",
            "space_0906_type": "cping",
            "space_0906_sort": 1
        },
        {
            "space_0906_source": "admob",
            "space_0906_id": "ca-app-pub-3940256099942544/1033173712AA",
            "space_0906_type": "cping",
            "space_0906_sort": 3
        }
    ]
}"""

    }
}