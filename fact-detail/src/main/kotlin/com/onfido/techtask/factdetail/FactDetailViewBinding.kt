package com.onfido.techtask.factdetail

import android.content.res.Resources
import android.view.View.OnClickListener
import com.airbnb.mvrx.withState
import com.onfido.techtask.BaseViewBinding
import com.onfido.techtask.NavigationViewBinding
import com.onfido.techtask.repodetail.R
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

internal class FactDetailViewBinding @AssistedInject constructor(
    fragment: FactDetailFragment,
    @Assisted override val viewModel: FactDetailViewModel,
    val navigationViewBinding: NavigationViewBinding,
    resources: Resources
) : BaseViewBinding<FactDetailState>(fragment, viewModel) {

    val onFavoriteClick = OnClickListener {
        viewModel.onFavoriteClicked()
    }

    val favoriteIcon: Int
        get() = withState(viewModel) {
            if (it.isFavorite() == true) {
                R.drawable.ic_favorited
            } else {
                R.drawable.ic_not_favorited
            }
        }

    val text: String
        get() = withState(viewModel) {
            it.text
        }

    val createdAt: String
        get() = withState(viewModel) {
            it.createdAt
        }

    init {
        navigationViewBinding.toolbarTitle =
            resources.getString(R.string.fact_detail_fragment_title)
    }

    @AssistedInject.Factory
    interface Factory {

        fun create(viewModel: FactDetailViewModel): FactDetailViewBinding
    }
}
