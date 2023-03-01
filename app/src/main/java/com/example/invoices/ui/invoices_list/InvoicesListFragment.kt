package com.example.invoices.ui.invoices_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.invoices.ui.adapter.base.FingerprintAdapter
import com.example.invoices.ui.adapter.base.decorations.GroupVerticalItemDecoration
import com.example.invoices.ui.adapter.base.decorations.HorizontalDividerItemDecoration
import com.example.invoices.ui.adapter.fingerprints.InvoiceFingerprint
import com.example.invoices.ui.base.BaseFragment
import com.example.invoices.utils.InvoicesApiType
import com.example.invoices.utils.observeEvent
import com.example.invoicestesttask.R
import com.example.invoicestesttask.databinding.FragmentListInvoicesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvoicesListFragment : BaseFragment() {

    private lateinit var binding: FragmentListInvoicesBinding
    override val viewModel by viewModels<InvoicesListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListInvoicesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding.choseProductionApiButton.setOnClickListener { viewModel.choseApiType(InvoicesApiType.Production) }
        binding.choseEmptyApiButton.setOnClickListener { viewModel.choseApiType(InvoicesApiType.Empty) }
        binding.choseMalformedApiButton.setOnClickListener { viewModel.choseApiType(InvoicesApiType.Malformed) }
    }

    private fun setupObservers() {
        viewModel.invoicesListLiveData.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as FingerprintAdapter).setItems(it)
        }
        viewModel.loadingDataLiveData.observe(viewLifecycleOwner) { isVisible ->
            if (isVisible) {
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
        viewModel.showEmptyMessageResEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerViews() {
        binding.recyclerView.apply {
            adapter = FingerprintAdapter(getFingerprints())
            layoutManager = LinearLayoutManager(requireContext())

            addItemDecoration(HorizontalDividerItemDecoration(70)) // addable
            addItemDecoration(
                GroupVerticalItemDecoration(
                    R.layout.item_invoice,
                    50,
                    100
                )
            ) // addable
        }
    }

    private fun getFingerprints() = listOf(
        InvoiceFingerprint(),
    )
}