package com.example.invoices.data.remote.apiModels.invoicesModels


import com.google.gson.annotations.SerializedName

data class InvoiceItemApi(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("priceinCents") val priceInCents: Int,
    @SerializedName("quantity") val quantity: Int
)