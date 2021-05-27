package com.onfido.techtask.factlist

import android.content.res.Resources
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.withState
import com.onfido.techtask.FragmentNavigation
import com.onfido.techtask.NavigationViewBinding
import com.onfido.techtask.ToastHelper
import com.onfido.techtask.data.FactDetailInput
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

internal class FactListViewBinding @AssistedInject constructor(
    fragment: FactListFragment,
    @Assisted override val viewModel: FactListViewModel,
    @Assisted val adapter: FactListAdapter,
    private val toastHelper: ToastHelper,
    val navigationViewBinding: NavigationViewBinding,
    private val navigation: FragmentNavigation,
    resources: Resources
) : com.onfido.techtask.BaseViewBinding<FactListState>(fragment, viewModel) {

    val catFactListItems: List<FactListItem>
        get() = withState(viewModel) {
            it.shownFacts
        }

    val shouldShowRefreshing: Boolean
        get() = withState(viewModel) {
            it.facts is Loading
        }

    val swipeToRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.fetchFacts()
    }

    val noResultVisibility
        get() = withState(viewModel) {
            if (it.shownFacts.isNullOrEmpty()) VISIBLE else GONE
        }

    init {
        navigationViewBinding.toolbarTitle = resources.getString(R.string.app_name)
        navigationViewBinding.toolbarMenuResId = R.menu.fact_list_search_menu
        navigationViewBinding.searchableCallback = { query ->
            viewModel.onNewQuery(query)
        }
        subscribeToFactClickedId()
        subscribeToSearchedQuery()
        subscribeToNewFacts()
        subscribeFavorites()
        subscribeToError()
    }

    private fun subscribeFavorites() {
        viewModel.asyncSubscribe(FactListState::favoriteRepositories, uniqueOnly()) {
            viewModel.onFavoritesLoaded(it)
        }
    }

    private fun subscribeToSearchedQuery() {
        viewModel.selectSubscribeNonNull(FactListState::searchedQuery, uniqueOnly()) {
            viewModel.fetchFacts()
        }
    }

    private fun subscribeToError() {
        viewModel.selectSubscribeNonNull(FactListState::hasError, uniqueOnly()) { hasError ->
            if (hasError) {
                toastHelper.show(context.getString(R.string.something_went_wrong))
                viewModel.onError()
            }
        }
    }

    private fun subscribeToNewFacts() {
        viewModel.asyncSubscribe(FactListState::facts, uniqueOnly()) {
            viewModel.onNewFactsLoad(it)
        }
    }

    private fun subscribeToFactClickedId() {
        viewModel.selectSubscribeNonNull(
            FactListState::clickedFact
        ) { repo ->
            navigation.navigate(
                R.id.factDetailFragment,
                FactDetailInput(
                    id = repo.id,
                    text = repo.text,
                    createdAt = repo.createdAt
                )
            )
            viewModel.onNavigatedToFactDetail()
        }
    }

    @AssistedInject.Factory
    interface Factory {

        fun create(
            viewModel: FactListViewModel,
            adapter: FactListAdapter
        ): FactListViewBinding
    }
}
