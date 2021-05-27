package com.onfido.techtask.factdetail

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.onfido.techtask.data.FactDetailInput

data class FactDetailState(
    val factId: Int,
    val text: String,
    val createdAt: String,
    val isFavorite: Async<Boolean> = Uninitialized
) : MvRxState {
    constructor(args: FactDetailInput) : this(
        factId = args.id,
        text = args.text,
        createdAt = args.createdAt
    )
}
