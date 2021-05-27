package com.onfido.techtask.factlist

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.onfido.techtask.ViewModelFactory
import com.onfido.techtask.data.FactFavorite
import com.onfido.techtask.ktx.createViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

internal class FactListViewModel @AssistedInject constructor(
    private val repository: FactListRepository,
    @Assisted initialState: FactListState
) : com.onfido.techtask.BaseViewModel<FactListState>(initialState) {

    init {
        fetchFacts()
    }

    private fun observeFavorites() {
        repository.observeFavorites()
            .execute {
                copy(favoriteRepositories = it)
            }
    }

    fun fetchFacts() {
        repository.fetchFacts().execute {
            copy(facts = it, hasError = it is Fail)
        }
    }

    fun onNewFactsLoad(newRepositories: List<FactListItem>) = setState {
        observeFavorites()
        copy(shownFacts = newRepositories)
    }

    fun onError() = setState {
        copy(hasError = false)
    }

    fun onNewQuery(query: String) = setState {
        copy(
            shownFacts = facts().filterBySearchQuery(query).updateFavorites(favoriteRepositories())
        )
    }

    fun onFactClick(factsListItem: FactListItem) = setState {
        copy(clickedFact = factsListItem)
    }

    fun onNavigatedToFactDetail() = setState {
        copy(clickedFact = null)
    }

    fun onFavoritesLoaded(favorites: List<FactFavorite>) = setState {
        copy(shownFacts = shownFacts.updateFavorites(favorites))
    }

    fun onFavoriteClicked(item: FactListItem) = withState {
        val isFavorite = item.isFavorite
        val repo = FactFavorite(item.id)

        if (isFavorite) {
            repository.unfavoriteRepo(repo)
        } else {
            repository.favoriteRepo(repo)
        }
    }

    @AssistedInject.Factory
    interface Factory : ViewModelFactory<FactListState> {
        override fun create(initialState: FactListState): FactListViewModel
    }

    companion object :
        MvRxViewModelFactory<FactListViewModel, FactListState> {

        private fun List<FactListItem>?.filterBySearchQuery(query: String) =
            this?.filter { it.text.contains(query, true) } ?: emptyList()

        override fun create(
            viewModelContext: ViewModelContext,
            state: FactListState
        ): FactListViewModel =
            viewModelContext.createViewModel(state)

        private fun List<FactListItem>.updateFavorites(favorites: List<FactFavorite>?) =
            map { listItem ->
                val isFavorite = favorites?.any { it.factId == listItem.id } ?: false
                listItem.copy(isFavorite = isFavorite)
            }
    }
}
