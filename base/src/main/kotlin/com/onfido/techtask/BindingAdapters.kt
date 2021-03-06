package com.onfido.techtask

import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide

@BindingAdapter("bindableAdapter", "data")
fun <D : Any, A : ListAdapter<D, *>> RecyclerView.setRecycleViewProperties(
    bindableAdapter: A,
    items: List<D>?
) {

    if (adapter != bindableAdapter) adapter = bindableAdapter
    items?.let {
        bindableAdapter.submitList(it)
    }
}

@BindingAdapter("swipeToRefreshListener")
fun SwipeRefreshLayout.setSwipeToRefreshLayout(callback: SwipeRefreshLayout.OnRefreshListener) {
    setOnRefreshListener(callback)
    setColorSchemeResources(R.color.colorGreen)
}

@BindingAdapter("url")
fun ImageView.loadFromUrl(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_loading)
        .into(this)
}

@BindingAdapter("inflateMenu")
fun Toolbar.setInflateMenu(toolbarMenuResId: Int) {
    if (menu.size() == 0) inflateMenu(toolbarMenuResId)
}

@BindingAdapter("searchableCallback")
fun Toolbar.setSearchable(query: (String) -> Unit) {
    val searchMenuItem = menu.children.firstOrNull { it.actionView is SearchView }
    val searchView = searchMenuItem?.actionView as? SearchView
    searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            if (!searchView.isIconified) {
                searchView.isIconified = true
            }
            query(query)
            return true
        }

        override fun onQueryTextChange(s: String): Boolean = true
    })

    searchMenuItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            query("")
            return true
        }
    })
}

@BindingAdapter("onLoadMore")
fun RecyclerView.setOnLoadMoreCallback(onLoadMore: () -> Unit) {
    clearOnScrollListeners()
    addOnScrollListener(object : RecyclerView.OnScrollListener() {

        var isLoading = false
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val manager = layoutManager as LinearLayoutManager
            if (manager.findLastVisibleItemPosition() == (adapter?.itemCount ?: 0) - 1 &&
                isLoading.not()
            ) {
                isLoading = true
                onLoadMore()
            } else {
                isLoading = false
            }
        }
    })
}

@BindingAdapter("setResIcon")
fun ImageView.setResIcon(resId: Int) {
    setImageResource(resId)
}
