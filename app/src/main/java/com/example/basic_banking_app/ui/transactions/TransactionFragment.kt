package com.example.basic_banking_app.ui.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basic_banking_app.databinding.FragmentTransactionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private var binding: FragmentTransactionBinding? = null
    private lateinit var transactionsAdapter: TransactionsAdapter
    private val transactionViewModel: TransactionViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)

        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         transactionsAdapter = TransactionsAdapter()
        binding?.recTransactionsList?.adapter = transactionsAdapter
        binding?.recTransactionsList?.layoutManager = LinearLayoutManager(requireContext())

        transactionViewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            transactions.let { transactionsAdapter.submitList(it) }
        }
    }
}