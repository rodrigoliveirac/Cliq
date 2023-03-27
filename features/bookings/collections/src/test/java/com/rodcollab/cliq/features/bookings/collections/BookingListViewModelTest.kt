package com.rodcollab.cliq.features.bookings.collections

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rodcollab.cliq.features.bookings.collections.domain.GetBookingsUseCase
import com.rodcollab.cliq.features.bookings.collections.model.BookingItem
import com.rodcollab.cliq.features.bookings.collections.ui.BookingListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BookingListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val scope = TestCoroutineRule()

    private val getBookingsUseCase = mock<GetBookingsUseCase>()

    private val viewModel = BookingListViewModel(getBookingsUseCase)

    @Test
    fun `Verify UiState is initialized with bookings filter by default date`() =
        scope.runTest {

            whenever(getBookingsUseCase(LocalDate.now().toString()))
                .thenReturn(
                    listOf(
                        BookingItem(
                            id = "id",
                            bookedClientName = "name",
                            bookedClientAddress = "clientAddress",
                            bookedTime = 6000000,
                            bookedDate = "2023-03-27",
                        )
                    )
                )


            viewModel.onResume()

            val uiState = async {
                viewModel.stateOnceAndStream().getOrAwaitValue()
            }

            val result = uiState.await()

            assert(result.bookingList.any { it.bookedDate == LocalDate.now().toString() })
        }
}