package com.frndzcode.client.example_room_hilt.app

class MyConstants{
    object API_CONST{
        const val API_KEY = "99379fb2edc0417f9aeefba9a3128bce"
        const val BASE_URL = "https://newsapi.org"
        const val SEARCH_NEWS_TIME_DELAY = 500L
        const val QUERY_PAGE_SIZE = 20
    }

    object COMMON_CONST{
        const val USERNAME_MATCHES = "^[a-zA-Z0-9._-]{3,20}$"
        const val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    }

}

