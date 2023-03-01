package com.example.invoices.utils

object InvoicesApiChooser {
    var type: InvoicesApiType = InvoicesApiType.Production
}

sealed class InvoicesApiType {
    object Production : InvoicesApiType()
    object Empty : InvoicesApiType()
    object Malformed : InvoicesApiType()
}