package com.example.invoices.ui.invoices_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.invoices.data.repositories.InvoiceRepository
import com.example.invoices.ui.base.BaseViewModel
import com.example.invoices.ui.models.invoices.Invoice
import com.example.invoices.utils.InvoicesApiChooser
import com.example.invoices.utils.InvoicesApiType
import com.example.invoices.utils.MutableLiveEvent
import com.example.invoices.utils.publishEvent
import com.example.invoices.utils.share
import com.example.invoicestesttask.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoicesListViewModel @Inject constructor(
    private val invoiceRepository: InvoiceRepository
) : BaseViewModel() {

    private val _invoicesListLiveData = MutableLiveData<List<Invoice>>()
    val invoicesListLiveData = _invoicesListLiveData.share()

    private val _loadingDataLiveData = MutableLiveData<Boolean>()
    val loadingDataLiveData = _loadingDataLiveData.share()

    private val _showEmptyMessageResEvent = MutableLiveEvent<Int>()
    val showEmptyMessageResEvent = _showEmptyMessageResEvent.share()


    private fun loadInvoices() = viewModelScope.launch {
        _loadingDataLiveData.postValue(true)
        invoiceRepository.getAllInvoices().handleNetworkResult(
            successBlock = { allInvoices ->
                if (allInvoices.isEmpty()) _showEmptyMessageResEvent.publishEvent(R.string.empty)
                else _invoicesListLiveData.postValue(allInvoices)
            },
            finalBlock = {
                _loadingDataLiveData.postValue(false)
            }
        )
    }

    fun choseApiType(type: InvoicesApiType) {
        InvoicesApiChooser.type = type
        loadInvoices()
    }
}