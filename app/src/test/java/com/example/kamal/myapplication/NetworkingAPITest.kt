package com.example.kamal.myapplication

import com.example.kamal.myapplication.model.GameItemDetailModel
import com.example.kamal.myapplication.network.APIInterface
import com.example.kamal.myapplication.network.ApiClient.clientAuthentication
import com.example.kamal.myapplication.repository.RetrofitRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NetworkingAPITest {

    private lateinit var retrofitRepo: RetrofitRepository
    private var itemId = ""

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        retrofitRepo = RetrofitRepository()
        itemId = "810321"
    }

    @Test
    fun `get game detail test`() {
        val apiInterface = clientAuthentication!!.create(APIInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiInterface.getItemDetail(itemId) as GameItemDetailModel?
            assertEquals(false, response?.description?.isEmpty() == true)
        }
    }

    @After
    fun clearValues() {
        itemId = ""
    }

}