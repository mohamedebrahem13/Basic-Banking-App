package com.example.basic_banking_app.ui.trensfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basic_banking_app.R
import com.example.basic_banking_app.data.model.Client
import com.example.basic_banking_app.databinding.FragmentTransferBinding
import com.example.basic_banking_app.ui.clients.ClientAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferFragment : Fragment() {

    private var binding: FragmentTransferBinding? = null
    private val transferViewModel: TransferViewModel by viewModels()

    private var amount: Float = 0.0f
    private var transferor: String = ""
    private var transferorID: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transferor= TransferFragmentArgs.fromBundle(requireArguments()).transferor
        amount= TransferFragmentArgs.fromBundle(requireArguments()).amount
        transferorID= TransferFragmentArgs.fromBundle(requireArguments()).transferorId

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val clientAdapter = ClientAdapter(ClientAdapter.ItemClickListener{
            insertTransaction(it)
        })
        binding!!.recReceiversList.adapter = clientAdapter

        binding?.recReceiversList?.layoutManager = LinearLayoutManager(requireContext())
        transferViewModel.clientlistLivedata.observe(viewLifecycleOwner) { clients ->
            val list = clients.toMutableList()
            list.removeAt(transferorID - 1)
            clientAdapter.submitList(list)
        }

        transferViewModel.completeTask.observe(viewLifecycleOwner) { isTaskCompleted ->
            if (isTaskCompleted == true) {
                Toast.makeText(requireContext(), "Transaction Successfully Completed", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_transferFragment_to_clientFragment)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding!!.root    }

    private fun insertTransaction(client: Client) {
        transferViewModel.insertTransaction(amount.toDouble(),transferor,client)
    }
}
