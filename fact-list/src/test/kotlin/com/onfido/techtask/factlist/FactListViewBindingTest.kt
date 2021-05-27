package com.onfido.techtask.factlist

import android.content.res.Resources
import com.airbnb.mvrx.test.MvRxTestRule
import com.onfido.techtask.FragmentNavigation
import com.onfido.techtask.data.FactDetailInput
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import io.reactivex.Observable
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class FactListViewBindingTest {

    private val resources = mock<Resources> {
        on { getString(R.string.app_name) } doReturn ANY_APP_NAME
    }
    private val fragment = FactListFragment()
    private val repository = mock<FactListRepository> {
        on { fetchFacts() } doReturn Single.just(
            listOf(
                FactListItem(
                    text = ANY_FACT_TEXT,
                    isVerified = false,
                    isNew = false,
                    createdAt = ANY_FACT_CREATED_AT,
                    isFavorite = false
                ),

                FactListItem(
                    text = SEARCHED_FACT_TEXT,
                    isVerified = false,
                    isNew = false,
                    createdAt = ANY_FACT_CREATED_AT,
                    isFavorite = false
                )
            )
        )

        on { observeFavorites() } doReturn Observable.empty()
    }
    private val viewModel = testViewModel(repository)
    private val viewBinding =
        testViewBinding(fragment = fragment, viewModel = viewModel, resources = resources)

    @Test
    fun `given view binding initialised the title should be right one`() {
        assertThat(viewBinding.navigationViewBinding.toolbarTitle).isEqualTo(ANY_APP_NAME)
    }

    @Test
    fun `given view binding initialised adapter should be repo adapter`() {
        assertThat(viewBinding.adapter).isInstanceOf(FactListAdapter::class.java)
    }

    @Test
    fun `given view binding initialised loading indicator should be invisible`() {
        assertThat(viewBinding.shouldShowRefreshing).isFalse()
    }

    @Test
    fun `given clickedRepo, should navigate`() {
        val clickedFact = FactListItem(
            text = ANY_FACT_TEXT,
            isNew = false,
            isVerified = false,
            createdAt = ANY_FACT_CREATED_AT
        )
        val viewModel = testViewModel(repository) {
            copy(clickedFact = clickedFact)
        }
        val navigation = mock<FragmentNavigation>()
        testViewBinding(fragment, viewModel = viewModel, navigation = navigation)

        then(navigation).should().navigate(
            R.id.factDetailFragment,
            FactDetailInput(
                id = clickedFact.id,
                text = clickedFact.text,
                createdAt = clickedFact.createdAt
            )
        )
    }

    companion object {
        private const val ANY_APP_NAME = "Cat Facts"
        private const val ANY_FACT_TEXT = "text"
        private const val SEARCHED_FACT_TEXT = "filtered"
        private const val SEARCH_QUERY = "filtered"
        private const val ANY_FACT_CREATED_AT = "created"

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}
