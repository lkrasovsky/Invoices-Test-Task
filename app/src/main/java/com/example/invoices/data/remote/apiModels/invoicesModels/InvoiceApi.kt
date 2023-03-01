package com.example.invoices.data.remote.apiModels.invoicesModels


import com.google.gson.annotations.SerializedName

data class InvoiceApi(
    @SerializedName("date") val date: String,
    @SerializedName("id") val id: String,
    @SerializedName("description") val description: String?,
    @SerializedName("items") val items: List<InvoiceItemApi>
)
