package com.onfido.techtask.factdetail

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.onfido.techtask.BaseFragment
import com.onfido.techtask.HasViewModelFactory
import com.onfido.techtask.ViewModelFactory
import com.onfido.techtask.repodetail.R
import com.onfido.techtask.repodetail.databinding.FactDetailFragmentBinding
import javax.inject.Inject

class FactDetailFragment :
    BaseFragment<FactDetailFragmentBinding>(),
    HasViewModelFactory<FactDetailState> {

    @Inject
    internal lateinit var viewModelFactory: FactDetailViewModel.Factory

    @Inject
    internal lateinit var viewBindingFactory: FactDetailViewBinding.Factory

    override val factory: ViewModelFactory<FactDetailState>
        get() = viewModelFactory

    override val layoutId: Int
        get() = R.layout.fact_detail_fragment

    private val viewModel by fragmentViewModel(FactDetailViewModel::class)

    override fun invalidate() = viewDataBinding.viewBinding!!.notifyChange()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewBinding = viewBindingFactory.create(viewModel)
    }
}
