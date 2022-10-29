package com.frndzcode.client.example_room_hilt.app

class MyConstants{
    object API_CONST{
        const val API_KEY = "99379fb2edc0417f9aeefba9a3128bce"
        const val BASE_URL = "https://newsapi.org/"
        const val SEARCH_NEWS_TIME_DELAY = 500L
        const val QUERY_PAGE_SIZE = 20

        const val top_headlines = "v2/top-headlines"
    }

    object COMMON_CONST{
        const val USERNAME_MATCHES = "^[a-zA-Z0-9._-]{3,20}$"
        const val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    }

    object SOCIAL_MEDIA{
        //facebook
        const val FB_APP_ID = "346170954376840"
        const val FB_APP_SECRET = "2b778815cd83d0ea930fdc14b5089c65"
        const val FB_CLIENT_TOKEN = "6c60889eb6b91ff5e5af96990d86279b"

        //google
        const val CLIENT_ID = "683662534936-aaildetn2ki417u0m9n9i90vnuei9s1j.apps.googleusercontent.com"
        const val CLIENT_SECRET = "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"

        //login type
        const val FACEBOOK = "FACEBOOK"
        const val LINKEDIN = "LINKEDIN"
        const val GOOGLE = "GOOGLE"
    }

    object STATIC_OBJ{
        const val DATA = 1
        const val EXTRA = 11
        const val DOCTOR = 1
        const val DEPARTMENT = 2
        const val LOADING = 0
        const val NO_DATA = -1
        const val NO_INTERNET = -2
        const val DEFAULT_SEARCH = 0
        const val CHECK_PERMISSIONS = 0x12
        const val PICK_DOCUMENT = 0x45
        const val PICK_GALLERY = 0x46
        const val PICK_CAMERA = 0x47
    }

}

