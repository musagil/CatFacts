package com.onfido.techtask.factlist

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FactModel(
    @Json(name = "status") val status: Status,
    @Json(name = "text") val text: String,
    @Json(name = "createdAt") val createdAt: String
) {

    @JsonClass(generateAdapter = true)
    data class Status(
        @Json(name = "verified") val verified: Boolean,
        @Json(name = "sentCount") val sentCount: Int
    )
}
