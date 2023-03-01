package com.example.invoices.ui.adapter.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.invoices.ui.adapter.base.BaseViewHolder
import com.example.invoices.ui.adapter.base.Item
import com.example.invoices.ui.adapter.base.ItemFingerprint
import com.example.invoices.ui.models.invoices.InvoiceItem
import com.example.invoicestesttask.R
import com.example.invoicestesttask.databinding.ItemInvoiceItemBinding

class InvoiceItemFingerprint : ItemFingerprint<ItemInvoiceItemBinding, InvoiceItem> {

    override fun isRelativeItem(item: Item) = item is InvoiceItem

    override fun getLayoutId() = R.layout.item_invoice_item

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemInvoiceItemBinding, InvoiceItem> {
        val binding = ItemInvoiceItemBinding.inflate(layoutInflater, parent, false)
        return InvoiceItemViewHolder(binding)
    }
}

class InvoiceItemViewHolder(
    binding: ItemInvoiceItemBinding
) : BaseViewHolder<ItemInvoiceItemBinding, InvoiceItem>(binding) {

    override fun onBind(item: InvoiceItem) = with(binding) {
        binding.invoiceItemServiceNameTextView.text = item.name
        binding.invoiceItemQuantityTextView.text = item.quantity.toString()
        binding.invoiceItemCost.text = item.priceInCents.toString()
    }
}