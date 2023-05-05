package com.example.basic_banking_app.ui.detail

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basic_banking_app.R
import com.example.basic_banking_app.data.model.Client
import com.example.basic_banking_app.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private var binding: FragmentDetailBinding? = null
    private lateinit var currentClient: Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         currentClient = DetailFragmentArgs.fromBundle(requireArguments()).client
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding!!.client= currentClient

        binding?.btnTransferMoney?.setOnClickListener {
            showDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding!!.root    }

    private fun showDialog() {
        val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val editAmount = EditText(requireContext())
        editAmount.inputType = InputType.TYPE_CLASS_NUMBER
        editAmount.setRawInputType(Configuration.KEYBOARD_12KEY)
        editAmount.hint = "Amount"
        editAmount.setSingleLine()
        alert.setTitle(getString(R.string.EnterAmount))
        alert.setView(editAmount)

        alert.setPositiveButton("OK") { dialog, which -> }
        alert.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        val dialog = alert.create()

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val amountString: String = editAmount.text.toString().trim()
                    if (amountString.isEmpty()) {
                        editAmount.error = getString(R.string.AnountCanNotBeEmpty)
                        return
                    } else {
                        val amount: Double = amountString.toDouble()
                        if (amount > currentClient.balance){
                            editAmount.error = getString(R.string.YouDoNotHaveEnophBalance)
                            return
                        }
                        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToTransferFragment2(currentClient.name,currentClient.client_id, amount.toFloat()))
                        dialog.dismiss()


                    }
                }
            })
    }

}