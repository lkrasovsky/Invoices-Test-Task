package com.example.invoices.utils

import android.annotation.SuppressLint
import com.example.invoices.data.remote.apiModels.invoicesModels.InvoiceApi
import com.example.invoices.data.remote.apiModels.invoicesModels.InvoiceItemApi
import com.example.invoices.ui.models.invoices.Invoice
import com.example.invoices.ui.models.invoices.InvoiceItem
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun InvoiceApi.mapToInvoice(): Invoice {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val output = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val d: Date = sdf.parse(date) ?: Date()
    val formattedTime: String = output.format(d)

    return Invoice(
        date = formattedTime,
        id = id,
        description = description
            ?: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        items = items.map { it.toInvoiceItem() },
        totalCost = items.fold(0.0) { acc, invoiceItemApi -> acc + (invoiceItemApi.priceInCents * invoiceItemApi.quantity) }
    )
}

fun InvoiceItemApi.toInvoiceItem(): InvoiceItem {
    return InvoiceItem(
        id = id,
        name = name,
        priceInCents = priceInCents,
        quantity = quantity
    )
}