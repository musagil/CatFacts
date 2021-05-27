package com.onfido.techtask.factlist

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.onfido.techtask.data.FactFavorite

data class FactListState(
    val facts: Async<List<FactListItem>> = Uninitialized,
    val favoriteRepositories: Async<List<FactFavorite>> = Uninitialized,
    val shownFacts: List<FactListItem> = emptyList(),
    val clickedFact: FactListItem? = null,
    val searchedQuery: String? = null,
    val hasError: Boolean = false
) : MvRxState
