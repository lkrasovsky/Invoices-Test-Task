package com.example.invoices.ui.adapter.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.invoices.ui.adapter.base.BaseViewHolder
import com.example.invoices.ui.adapter.base.FingerprintAdapter
import com.example.invoices.ui.adapter.base.Item
import com.example.invoices.ui.adapter.base.ItemFingerprint
import com.example.invoices.ui.adapter.base.decorations.HorizontalDividerDecoration
import com.example.invoices.ui.models.invoices.Invoice
import com.example.invoicestesttask.R
import com.example.invoicestesttask.databinding.ItemInvoiceBinding

class InvoiceFingerprint : ItemFingerprint<ItemInvoiceBinding, Invoice> {

    override fun isRelativeItem(item: Item) = item is Invoice

    override fun getLayoutId() = R.layout.item_invoice

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemInvoiceBinding, Invoice> {
        val binding = ItemInvoiceBinding.inflate(layoutInflater, parent, false)
        return InvoiceViewHolder(binding)
    }
}

class InvoiceViewHolder(
    binding: ItemInvoiceBinding
) : BaseViewHolder<ItemInvoiceBinding, Invoice>(binding) {

    override fun onBind(item: Invoice): Unit = with(binding) {
        binding.invoiceDateTextView.text = item.date
        binding.invoiceDescriptionTextView.text = item.description
        binding.totalCostTextView.text = item.totalCost.toString()

        val makeRecyclerView: RecyclerView.() -> Unit = {
            adapter = FingerprintAdapter(
                listOf(
                    InvoiceItemFingerprint()
                )
            )
            layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(HorizontalDividerDecoration(50, 0))
        }

        binding.invoiceItemsRecyclerView.apply {
            if (adapter == null) makeRecyclerView()
        }

        (binding.invoiceItemsRecyclerView.adapter as FingerprintAdapter)
            .setItems(item.items)
    }
}


