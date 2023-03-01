package com.example.invoices.ui.models.invoices

import com.example.invoices.ui.adapter.base.Item


data class InvoiceItem(
    val id: String,
    val name: String,
    val priceInCents: Int,
    val quantity: Int
) : Item