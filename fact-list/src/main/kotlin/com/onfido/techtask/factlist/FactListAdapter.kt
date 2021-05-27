package com.onfido.techtask.factlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

internal class FactListAdapter(
    private val viewModel: FactListViewModel
) : com.onfido.techtask.BindableListAdapter<FactListItem, FactListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            parent,
            FactListItemViewBinding(),
            viewModel
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int) = getItem(position).id.toLong()

    class ViewHolder(
        parent: ViewGroup,
        private val viewBinding: FactListItemViewBinding,
        private val viewModel: FactListViewModel
    ) : RecyclerView.ViewHolder(
        viewBinding.createView(parent)
    ) {

        fun bind(factListItem: FactListItem) {
            viewBinding.bindItem(factListItem, viewModel)
        }
    }
}
