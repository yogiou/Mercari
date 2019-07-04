package com.jiewen.mercari

class Config {
    companion object {
        lateinit var instance: Config

        const val NETWORK_TIMEOUT = 15L
        const val BASE_API_DOMAIN = "s3-ap-northeast-1.amazonaws.com/"
        const val BASE_API_URL = "https://$BASE_API_DOMAIN"
    }
}