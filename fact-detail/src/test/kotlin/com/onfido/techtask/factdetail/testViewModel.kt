package com.onfido.techtask.factdetail

import com.onfido.techtask.data.FactDetailInput
import com.nhaarman.mockitokotlin2.mock

internal fun testViewModel(
    repository: FactDetailRepository = mock(),
    factDetailInput: FactDetailInput = mock(),
    reduce: FactDetailState.() -> FactDetailState = { this }
) = FactDetailViewModel(
    repository,
    FactDetailState(factDetailInput).reduce()
)
