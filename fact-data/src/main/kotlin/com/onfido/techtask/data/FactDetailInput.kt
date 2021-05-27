package com.onfido.techtask.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FactDetailInput(
    val id: Int,
    val text: String,
    val createdAt: String
) : Parcelable
