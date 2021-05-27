package com.onfido.techtask.factlist

import com.onfido.techtask.network.Cacheable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import retrofit2.http.GET

internal interface FactListRequests {

    @CheckReturnValue
    @Cacheable
    @GET("b/6064467b418f307e2585ef1b")
    fun getCatFacts(): Single<List<FactModel>>
}
