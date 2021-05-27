package com.onfido.techtask.factlist

import com.nhaarman.mockitokotlin2.mock

internal fun testViewModel(
    repository: FactListRepository = mock(),
    reduce: FactListState.() -> FactListState = { this }
) = FactListViewModel(
    repository,
    FactListState().reduce()
)
