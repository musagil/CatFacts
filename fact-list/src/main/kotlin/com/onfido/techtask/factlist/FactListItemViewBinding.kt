package com.onfido.techtask.factlist

import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.airbnb.mvrx.withState
import com.onfido.techtask.factlist.databinding.FactListAdapterItemBinding

internal class FactListItemViewBinding :
    com.onfido.techtask.AdapterItemViewBinding<FactListItem, FactListAdapterItemBinding, FactListViewModel>() {

    override fun createView(parent: ViewGroup) =
        parent.inflate(R.layout.fact_list_adapter_item)

    val name: String
        get() = item.text

    val favoriteIcon: Int
        get() = withState(viewModel) {
            if (item.isFavorite) {
                R.drawable.ic_favorited
            } else {
                R.drawable.ic_not_favorited
            }
        }

    val newIconVisibility: Int
        get() = withState(viewModel) {
            if (item.isNew) {
                VISIBLE
            } else {
                GONE
            }
        }

    val verifiedIconVisibility: Int
        get() = withState(viewModel) {
            if (item.isVerified) {
                VISIBLE
            } else {
                GONE
            }
        }

    val onFavoriteClick = OnClickListener {
        viewModel.onFavoriteClicked(item)
    }

    val itemClickListener = OnClickListener {
        viewModel.onFactClick(item)
    }
}
