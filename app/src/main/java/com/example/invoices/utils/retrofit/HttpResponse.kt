package com.example.invoices.utils.retrofit

interface HttpResponse {
    val statusCode: Int
    val statusMessage: String?
    val url: String?
}