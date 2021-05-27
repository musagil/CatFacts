package com.onfido.techtask.factlist

import android.content.res.Resources
import com.onfido.techtask.FragmentNavigation
import com.onfido.techtask.NavigationViewBinding
import com.onfido.techtask.ToastHelper
import com.nhaarman.mockitokotlin2.mock

internal fun testViewBinding(
    fragment: FactListFragment = FactListFragment(),
    reduce: FactListState.() -> FactListState = { this },
    resources: Resources = mock(),
    toastHelper: ToastHelper = mock(),
    navigation: FragmentNavigation = mock(),
    viewModel: FactListViewModel = testViewModel(reduce = reduce)
) = FactListViewBinding(
    fragment,
    viewModel,
    FactListAdapter(viewModel),
    toastHelper,
    NavigationViewBinding(fragment),
    navigation,
    resources
)
