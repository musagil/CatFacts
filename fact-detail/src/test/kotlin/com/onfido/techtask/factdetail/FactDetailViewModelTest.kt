package com.onfido.techtask.factdetail

import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.onfido.techtask.data.FactDetailInput
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class FactDetailViewModelTest {

    @Test
    fun `given a favorite fact, should set proper state`() {
        val repository = mock<FactDetailRepository> {
            on { observeRepo(any()) } doReturn Observable.just(true)
        }
        val viewModel = testViewModel(repository = repository, factDetailInput = ANY_FACT_INPUT)
        viewModel.observeFavorite()

        withState(viewModel) {
            assertThat(it.isFavorite()).isEqualTo(true)
        }
    }

    @Test
    fun `given a not favorite fact, should set proper state`() {
        val repository = mock<FactDetailRepository> {
            on { observeRepo(any()) } doReturn Observable.just(false)
        }
        val viewModel = testViewModel(repository = repository, factDetailInput = ANY_FACT_INPUT)
        viewModel.observeFavorite()

        withState(viewModel) {
            assertThat(it.isFavorite()).isEqualTo(false)
        }
    }

    companion object {
        private const val ANY_FACT_TEXT = "text"
        private const val ANY_FACT_CREATED_AT = "created"

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
