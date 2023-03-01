package com.example.invoices.data.repositories

import com.example.invoices.data.remote.apis.InvoicesApi
import com.example.invoices.ui.models.invoices.Invoice
import com.example.invoices.utils.InvoicesApiChooser
import com.example.invoices.utils.InvoicesApiType
import com.example.invoices.utils.mapToInvoice
import com.example.invoices.utils.retrofit.NetworkResult
import com.example.invoices.utils.retrofit.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


class InvoiceRepositoryImpl @Inject constructor(
    private val invoiceApiDataSource: InvoicesApi,
    private val externalScope: CoroutineScope
) : InvoiceRepository {

    override suspend fun getAllInvoices() = withContext(externalScope.coroutineContext) {
        delay(500)
        when (InvoicesApiChooser.type) {
            InvoicesApiType.Production -> {
                invoiceApiDataSource.getInvoices().map {
                    it.invoiceApiItems.map { invoiceApi ->
                        invoiceApi.mapToInvoice()
                    }
                }
            }

            InvoicesApiType.Malformed -> {
                invoiceApiDataSource.getMalformedInvoices().map {
                    it.invoiceApiItems.map { invoiceApi ->
                        invoiceApi.mapToInvoice()
                    }
                }
            }

            InvoicesApiType.Empty -> {
                invoiceApiDataSource.getEmptyInvoices().map {
                    it.invoiceApiItems.map { invoiceApi ->
                        invoiceApi.mapToInvoice()
                    }
                }
            }
        }
    }
}

interface InvoiceRepository {

    suspend fun getAllInvoices(): NetworkResult<List<Invoice>>
}