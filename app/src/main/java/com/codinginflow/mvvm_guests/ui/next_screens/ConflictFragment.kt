package com.codinginflow.mvvm_guests.ui.next_screens

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mvvm_guests.R

class ConflictFragment : Fragment(R.layout.conflict_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.action_conflictFragment_to_guestFragment)
        }
    }
}