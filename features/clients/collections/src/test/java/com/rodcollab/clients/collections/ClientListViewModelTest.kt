package com.rodcollab.clients.collections

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rodcollab.clients.collections.domain.GetClientsUseCase
import com.rodcollab.clients.collections.model.ClientItem
import com.rodcollab.clients.collections.ui.ClientListViewModel
import com.rodcollab.clients.collections.utils.TestCoroutineRule
import com.rodcollab.clients.collections.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ClientListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val scope = TestCoroutineRule()

    private val getClientsUseCase = mock<GetClientsUseCase>()


    private val viewModel = ClientListViewModel(getClientsUseCase = getClientsUseCase)


    @Test
    fun `Verify uiState is initialized with clients`() =

        scope.runTest {

           launch {

               whenever(getClientsUseCase()).thenReturn(
                   listOf(
                       ClientItem(
                           id = "ID",
                           name = "name",
                           lastName = "lastName",
                           address = "address"
                       )
                   )
               )
           }

            viewModel.onResume()

            val uiState = async {
                viewModel.stateOnceAndStream().getOrAwaitValue()
            }

            val result = uiState.await()


            assert(result.clientList.isNotEmpty())

        }
}