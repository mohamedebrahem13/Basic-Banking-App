package com.example.basic_banking_app.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.basic_banking_app.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.customersButton.setOnClickListener {
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToClientFragment()
            )
        }

        // Clicking on transactions button navigates to TransactionsFragment.
        binding.transactionButton.setOnClickListener {
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToTransactionFragment()
            )
        }

        return binding.root


    }


}