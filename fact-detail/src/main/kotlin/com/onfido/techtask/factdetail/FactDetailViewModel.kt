package com.onfido.techtask.factdetail

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.onfido.techtask.ViewModelFactory
import com.onfido.techtask.data.FactFavorite
import com.onfido.techtask.ktx.createViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

internal class FactDetailViewModel @AssistedInject constructor(
    private val repository: FactDetailRepository,
    @Assisted initialState: FactDetailState
) : com.onfido.techtask.BaseViewModel<FactDetailState>(initialState) {

    init {
        observeFavorite()
    }

    @AssistedInject.Factory
    interface Factory : ViewModelFactory<FactDetailState> {
        override fun create(initialState: FactDetailState): FactDetailViewModel
    }

    fun observeFavorite() = withState {
        repository.observeRepo(it.factId)
            .execute { isFavorite ->
                copy(isFavorite = isFavorite)
            }
    }

    fun onFavoriteClicked() = withState {
        val isFavorite = it.isFavorite() ?: false
        val fact = FactFavorite(it.factId)

        if (isFavorite) {
            repository.unfavoriteRepo(fact)
        } else {
            repository.favoriteRepo(fact)
        }
    }

    companion object : MvRxViewModelFactory<FactDetailViewModel, FactDetailState> {

        override fun create(
            viewModelContext: ViewModelContext,
            state: FactDetailState
        ): FactDetailViewModel =
            viewModelContext.createViewModel(state)
    }
}
