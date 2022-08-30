package com.codinginflow.mvvm_guests.ui.guests

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mvvm_guests.R
import mvvm_guests.databinding.FragmentGuestsBinding

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

                }
                ViewState.ConfirmationScreen -> {

                }
                ViewState.ConflictScreen -> {

                }
                ViewState.ErrorSnackbar -> {

                }
            }
        }

        // init more business
    }
}