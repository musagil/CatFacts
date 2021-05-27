package com.onfido.techtask.factdetail

import android.content.res.Resources
import com.airbnb.mvrx.test.MvRxTestRule
import com.onfido.techtask.repodetail.R
import com.onfido.techtask.data.FactDetailInput
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class FactDetailViewBindingTest {

    private val resources = mock<Resources> {
        on { getString(R.string.fact_detail_fragment_title) } doReturn FRAGMENT_NAME
    }
    private val fragment = FactDetailFragment()
    private val repository = mock<FactDetailRepository> {
        on { observeRepo(any()) } doReturn Observable.empty()
    }
    private val viewModel = testViewModel(
        repository = repository,
        factDetailInput = ANY_FACT_INPUT
    )
    private val viewBinding =
        testViewBinding(fragment = fragment, viewModel = viewModel, resources = resources)

    @Test
    fun `given view binding initialised the title should be right one`() {
        assertThat(viewBinding.navigationViewBinding.toolbarTitle).isEqualTo(FRAGMENT_NAME)
    }

    @Test
    fun `given a fact input, should set createAt properly`() {
        assertThat(viewBinding.createdAt).isEqualTo(ANY_FACT_CREATED_AT)
    }

    @Test
    fun `given a fact input, should set fact text properly`() {
        assertThat(viewBinding.text).isEqualTo(ANY_FACT_TEXT)
    }

    @Test
    fun `given a favorite fact, should show correct icon`() {

        val repository = mock<FactDetailRepository> {
            on { observeRepo(any()) } doReturn Observable.just(true)
        }
        val viewModel = testViewModel(repository = repository, factDetailInput = ANY_FACT_INPUT)
        val viewBinding =
            testViewBinding(fragment = fragment, resources = resources, viewModel = viewModel)
        assertThat(viewBinding.favoriteIcon).isEqualTo(R.drawable.ic_favorited)
    }

    @Test
    fun `given a NOT favorite fact, should show correct icon`() {

        val repository = mock<FactDetailRepository> {
            on { observeRepo(any()) } doReturn Observable.just(false)
        }
        val viewModel = testViewModel(repository = repository, factDetailInput = ANY_FACT_INPUT)
        val viewBinding =
            testViewBinding(fragment = fragment, resources = resources, viewModel = viewModel)
        assertThat(viewBinding.favoriteIcon).isEqualTo(R.drawable.ic_not_favorited)
    }

    companion object {
        private const val ANY_FACT_TEXT = "text"
        private const val ANY_FACT_CREATED_AT = "created"
        private const val FRAGMENT_NAME = "Fact Detail"

        private val ANY_FACT_INPUT = FactDetailInput(
            id = ANY_FACT_TEXT.hashCode(),
            text = ANY_FACT_TEXT,
            createdAt = ANY_FACT_CREATED_AT
        )

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}
