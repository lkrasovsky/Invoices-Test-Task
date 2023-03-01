package com.example.invoices.ui.models.invoices

import com.example.invoices.ui.adapter.base.Item

data class Invoice(
    val date: String,
    val id: String,
    val description: String,
    val items: List<InvoiceItem>,
    val totalCost: Double
) : Item