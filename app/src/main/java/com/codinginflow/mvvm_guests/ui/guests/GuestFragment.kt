package com.codinginflow.mvvm_guests.ui.guests

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mvvm_guests.R
import mvvm_guests.databinding.FragmentGuestsBinding
import mvvm_guests.databinding.MessageRuleBinding

@AndroidEntryPoint
class GuestFragment : Fragment(R.layout.fragment_guests) {

    private val viewModel: GuestViewModel by viewModels()

    private val guestAdapter by lazy {
        GuestsAdapter(itemCbCallback = { guestItem, checked ->
            // do business to check the checkbox checked state
            viewModel.checkGuest(guestItem, checked)
        })
    }

    private lateinit var viewBinding: FragmentGuestsBinding
    private lateinit var viewBindingRule: MessageRuleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentGuestsBinding.bind(view)

        initViews()

        initObservers()

    }

    private fun initViews() {
        viewBinding.apply {
            recyclerViewGuestsWithReservation.apply {
                adapter = guestAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewBinding.buttonToolbarBack.setOnClickListener {requireActivity().onBackPressed()}
        viewBinding.btnContinue.setOnClickListener {
            viewModel.setContinueState()
        }

        // init more views here
    }

    private fun initObservers() {
        viewModel.guestList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    guestAdapter.items = result.value
                }
                is Resource.Failure -> {
                    //add failure handling code
                }
                is Resource.Loading -> {
                    //add loading code
                }
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.EnableContinueButton -> {
                    viewBinding.btnContinue.isEnabled = it.enabled
                }
                ViewState.ConfirmationScreen -> {
                    //launch confirmation fragment
                }
                ViewState.ConflictScreen -> {
                    //launch conflict fragment
                }
                ViewState.ErrorSnackbar -> {
                    //create error snackbar
                    Snackbar.make(
                        viewBindingRule.tvRule,
                        "at least one Guest in the party must have a reservation. Guests without reservations must remain in the same booking party in order to enter.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        // init more business
    }
}