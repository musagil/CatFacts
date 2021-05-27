package com.onfido.techtask.factdetail

import android.content.res.Resources
import com.onfido.techtask.NavigationViewBinding
import com.nhaarman.mockitokotlin2.mock

internal fun testViewBinding(
    fragment: FactDetailFragment = FactDetailFragment(),
    reduce: FactDetailState.() -> FactDetailState = { this },
    resources: Resources = mock(),
    viewModel: FactDetailViewModel = testViewModel(reduce = reduce)
) = FactDetailViewBinding(
    fragment,
    viewModel,
    NavigationViewBinding(fragment),
    resources
)
