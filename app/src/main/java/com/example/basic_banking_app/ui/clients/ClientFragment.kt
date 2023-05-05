package com.example.basic_banking_app.ui.clients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.basic_banking_app.databinding.FragmentClientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientFragment : Fragment() {
    private var binding: FragmentClientBinding? = null
    private lateinit var clientAdapter:ClientAdapter
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClientBinding.inflate(inflater, container, false)

        clientAdapter = ClientAdapter(ClientAdapter.ItemClickListener { client ->
            mainViewModel.onClientClicked(client)
        })

        binding!!.recyclerView.adapter = clientAdapter

        // Observing the client liveData and submit any change to the adapter.
        mainViewModel.clientlistLivedata.observe(viewLifecycleOwner) { clients ->
            clientAdapter.submitList(clients)
        }
        mainViewModel.detailedClient.observe(viewLifecycleOwner) { client ->
            client?.let { it ->
                this.findNavController().navigate(
                    ClientFragmentDirections.actionClientFragmentToDetailFragment(it)
                )
                mainViewModel.doneNavigating()
            }
        }

        return binding?.root
    }


}