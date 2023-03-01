package com.example.invoices.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.invoices.baseTest.coroutines.CoroutineTestRule
import com.example.invoices.data.remote.apiModels.invoicesModels.InvoiceApi
import com.example.invoices.data.remote.apiModels.invoicesModels.InvoiceItemApi
import com.example.invoices.data.remote.apiModels.invoicesModels.InvoicesResponse
import com.example.invoices.data.remote.apis.InvoicesApi
import com.example.invoices.ui.models.invoices.Invoice
import com.example.invoices.ui.models.invoices.InvoiceItem
import com.example.invoices.utils.InvoicesApiChooser
import com.example.invoices.utils.InvoicesApiType
import com.example.invoices.utils.retrofit.NetworkResult
import com.example.invoices.utils.retrofit.asSuccess
import io.mockk.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule


@OptIn(ExperimentalCoroutinesApi::class)
class InvoiceRepositoryImplTest {

    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val defaultExternalScope = CoroutineScope(coroutineTestRule.testDispatcher)

    private lateinit var test: InvoiceRepository
    private lateinit var defaultInvoicesApi: InvoicesApi
    private lateinit var actualInvoicesItem: List<Invoice>

    private val defaultInvoiceResponse = InvoicesResponse(
        invoiceApiItems = listOf(
            InvoiceApi(
                "2022-10-01T10:22:32", "1", "1", listOf(
                    InvoiceItemApi("1", "1", 1, 1),
                    InvoiceItemApi("1", "1", 1, 1),
                    InvoiceItemApi("1", "1", 1, 1),
                )
            ),
            InvoiceApi(
                "2022-10-01T10:22:32", "2", "2", listOf(
                    InvoiceItemApi("2", "2", 2, 2),
                    InvoiceItemApi("2", "2", 2, 2),
                    InvoiceItemApi("2", "2", 2, 2),
                )
            ),
        )
    )

    private val defaultInvoicesItems = listOf<Invoice>(
        Invoice(
            "2022-10-01 10:22:32", "1", "1",
            listOf(
                InvoiceItem("1", "1", 1, 1),
                InvoiceItem("1", "1", 1, 1),
                InvoiceItem("1", "1", 1, 1),
            ), 3.toDouble()
        ),
        Invoice(
            "2022-10-01 10:22:32", "2", "2",
            listOf(
                InvoiceItem("2", "2", 2, 2),
                InvoiceItem("2", "2", 2, 2),
                InvoiceItem("2", "2", 2, 2)
            ), 12.toDouble()
        ),
    )

    @Before
    fun setup() {
        defaultInvoicesApi = mockk(relaxed = true)
        mockkObject(InvoicesApiChooser)
        test = InvoiceRepositoryImpl(defaultInvoicesApi, defaultExternalScope)
    }

    @After
    fun tearDown() {
        unmockkObject(InvoicesApiChooser)
    }

    @Test
    fun `should call production server when production api type is chosen `() = runTest {
        stubInvoicesApiChooserToReturn(InvoicesApiType.Production)

        test.getAllInvoices()

        checkRepositoryCallProductionServerApi()
    }

    @Test
    fun `should call production server when empty api type is chosen `() = runTest {
        stubInvoicesApiChooserToReturn(InvoicesApiType.Empty)

        test.getAllInvoices()

        checkRepositoryCallEmptyServerApi()
    }

    @Test
    fun `should call production server when malformed api type is chosen `() = runTest {
        stubInvoicesApiChooserToReturn(InvoicesApiType.Malformed)

        test.getAllInvoices()

        checkRepositoryCallMalformedServerApi()
    }

    @Test
    fun `should return mapped list of invoices`() = runTest {
        stubInvoicesApiChooserToReturn(InvoicesApiType.Production)
        stubReturnedInvoiceResponse(defaultInvoiceResponse)

        actualInvoicesItem = test.getAllInvoices().asSuccess().value

        checkRepositoryReturnedCorrectInvoicesList()
    }

    private fun stubReturnedInvoiceResponse(response: InvoicesResponse) {
        coEvery { defaultInvoicesApi.getInvoices() } returns NetworkResult.Success.HttpResponse(
            response,
            200
        )
    }

    private fun checkRepositoryReturnedCorrectInvoicesList() {
        Assert.assertEquals(actualInvoicesItem.size, defaultInvoicesItems.size)
        Assert.assertTrue(defaultInvoicesItems.containsAll(actualInvoicesItem))
    }

    private fun stubInvoicesApiChooserToReturn(type: InvoicesApiType) {
        every { InvoicesApiChooser.type } returns type
    }

    private fun checkRepositoryCallProductionServerApi() {
        coVerify(exactly = 1) { defaultInvoicesApi.getInvoices() }
        confirmVerified(defaultInvoicesApi)
    }

    private fun checkRepositoryCallEmptyServerApi() {
        coVerify(exactly = 1) { defaultInvoicesApi.getEmptyInvoices() }
        confirmVerified(defaultInvoicesApi)
    }

    private fun checkRepositoryCallMalformedServerApi() {
        coVerify(exactly = 1) { defaultInvoicesApi.getMalformedInvoices() }
        confirmVerified(defaultInvoicesApi)
    }


}