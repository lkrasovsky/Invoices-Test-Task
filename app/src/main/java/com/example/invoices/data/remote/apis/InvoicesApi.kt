package com.example.invoices.data.remote.apis

import com.example.invoices.data.remote.apiModels.invoicesModels.InvoicesResponse
import com.example.invoices.utils.retrofit.NetworkResult
import retrofit2.http.GET

interface InvoicesApi {

    @GET("/xmm-homework/invoices.json")
    suspend fun getInvoices(): NetworkResult<InvoicesResponse>

    @GET("/xmm-homework/invoices_empty.json")
    suspend fun getEmptyInvoices(): NetworkResult<InvoicesResponse>

    @GET("/xmm-homework/invoices_malformed.json")
    suspend fun getMalformedInvoices(): NetworkResult<InvoicesResponse>
}