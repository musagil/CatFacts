package com.onfido.techtask.factlist

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.onfido.techtask.HasViewModelFactory
import com.onfido.techtask.ViewModelFactory
import com.onfido.techtask.factlist.databinding.FactListFragmentBinding
import javax.inject.Inject

class FactListFragment :
    com.onfido.techtask.BaseFragment<FactListFragmentBinding>(),
    HasViewModelFactory<FactListState> {

    @Inject
    internal lateinit var viewModelFactory: FactListViewModel.Factory

    @Inject
    internal lateinit var viewBindingFactoryGitHub: FactListViewBinding.Factory

    override val factory: ViewModelFactory<FactListState>
        get() = viewModelFactory

    override val layoutId: Int
        get() = R.layout.fact_list_fragment

    private val viewModel by fragmentViewModel(FactListViewModel::class)

    private val adapter by lazy { FactListAdapter(viewModel) }

    override fun invalidate() = viewDataBinding.viewBinding!!.notifyChange()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewBinding = viewBindingFactoryGitHub.create(viewModel, adapter)
    }
}
