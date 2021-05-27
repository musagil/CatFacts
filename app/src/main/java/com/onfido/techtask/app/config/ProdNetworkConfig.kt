package com.onfido.techtask.app.config

import com.onfido.techtask.network.NetworkConfig
import com.onfido.techtask.network.NetworkInterceptor
import com.onfido.techtask.network.OfflineCacheInterceptor
import okhttp3.Interceptor
import javax.inject.Inject

class ProdNetworkConfig @Inject constructor(
    private val networkInterceptor: NetworkInterceptor,
    private val offlineCacheInterceptor: OfflineCacheInterceptor
) : NetworkConfig {
    override val baseUrl: String
        get() = "https://api.jsonbin.io/"

    override val networkInterceptors: List<Interceptor>
        get() = listOf(networkInterceptor)

    override val interceptors: List<Interceptor>
        get() = listOf(offlineCacheInterceptor)
}
