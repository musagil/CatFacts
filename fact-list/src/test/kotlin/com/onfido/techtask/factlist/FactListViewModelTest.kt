package com.onfido.techtask.factlist

import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class FactListViewModelTest {

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

    @Test
    fun `given that navigation to fact detail, clicked id should be cleared`() {
        viewModel.onNavigatedToFactDetail()

        withState(viewModel) {
            assertThat(it.clickedFact).isNull()
        }
    }

    @Test
    fun `given a search query, should filter properly`() {
        viewModel.onNewQuery(SEARCH_QUERY)

        withState(viewModel) {
            assertThat(it.shownFacts).isEqualTo(
                listOf(
                    FactListItem(
                        text = SEARCHED_FACT_TEXT,
                        isNew = false,
                        isVerified = false,
                        createdAt = ANY_FACT_CREATED_AT
                    )
                )
            )
        }
    }

    @Test
    fun `given list of facts, should show them`() {
        viewModel.onNewFactsLoad(
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
                    isNew = false,
                    isVerified = false,
                    createdAt = ANY_FACT_CREATED_AT
                )
            )
        )

        withState(viewModel) {
            assertThat(it.shownFacts).isEqualTo(
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
                        isNew = false,
                        isVerified = false,
                        createdAt = ANY_FACT_CREATED_AT
                    )
                )
            )
        }
    }

    companion object {
        private const val ANY_FACT_TEXT = "text"
        private const val SEARCHED_FACT_TEXT = "filtered"
        private const val SEARCH_QUERY = "filtered"
        private const val ANY_FACT_CREATED_AT = "created"

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}
