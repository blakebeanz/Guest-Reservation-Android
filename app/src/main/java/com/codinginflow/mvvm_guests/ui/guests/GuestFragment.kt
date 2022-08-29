package com.codinginflow.mvvm_guests.ui.guests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_guests.*
import kotlinx.android.synthetic.main.fragment_guests.view.*
import mvvm_guests.R
import mvvm_guests.databinding.FragmentGuestsBinding

@AndroidEntryPoint
class GuestFragment : Fragment(R.layout.fragment_guests) {
    private val viewModel: GuestViewModel by viewModels()

    /*private val guestAdapter by lazy {
        GuestsAdapter()
    }*/

    /*private var _viewBinding: FragmentGuestsBinding ?= null
    private val viewBinding get() = _viewBinding!!*/

    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentGuestsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //view.recycler_view_guests.setNes

        val binding = FragmentGuestsBinding.bind(view)
        val viewModel by viewModels<GuestViewModel>()
        val guestAdapter = GuestsAdapter()

        binding.apply {
            recyclerViewGuestsWithReservation.apply {
                adapter = guestAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        /*viewBinding.recyclerViewGuestsWithReservation.apply {
            adapter = guestAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }*/

        //viewModel.guests.observe(viewLifecycleOwner) {guestAdapter.submitList(it)}
        viewModel.guestList.observe(this) { result ->
            when(result) {
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
    }
}