package com.example.invoices.data.remote.apiModels.invoicesModels


import com.google.gson.annotations.SerializedName

data class InvoicesResponse(
    @SerializedName("items") val invoiceApiItems: List<InvoiceApi>
)