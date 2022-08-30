package com.codinginflow.mvvm_guests.ui.guests

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codinginflow.mvvm_guests.data.GuestDao
import com.codinginflow.mvvm_guests.data.GuestRecyclerViewItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GuestViewModel @ViewModelInject constructor(private val guestDao: GuestDao) : ViewModel() {

    private val _guestList =
        MutableLiveData<Resource<List<GuestRecyclerViewItem>>>()
    val guestList
        get() = _guestList

    private val _viewState =
        MutableLiveData<ViewState>()
    val viewState
        get() = _viewState

    init {
        getGuestListItems()
    }


    private fun getGuestListItems() = viewModelScope.launch {
        _guestList.postValue(Resource.Loading)
        val guests = guestDao.getGuests().first()

        // create recycler view
        val guestItemList = mutableListOf<GuestRecyclerViewItem>()
        guestItemList.add(GuestRecyclerViewItem.Title("These Guests Have a Reservation", 1))

        for (GuestRecyclerViewItem in guests) {
            if (GuestRecyclerViewItem.hasReservation) {
                guestItemList.add(GuestRecyclerViewItem)
            }
        }

        guestItemList.add(GuestRecyclerViewItem.Title("These Guests Need a Reservation", 2))

        for (GuestRecyclerViewItem in guests) {
            if (!GuestRecyclerViewItem.hasReservation) {
                guestItemList.add(GuestRecyclerViewItem)
            }
        }

        _guestList.postValue(Resource.Success(guestItemList))
    }

    fun checkGuest(guest: GuestRecyclerViewItem.Guest, checked: Boolean) {
        // check if any checkbox is checked, we enable the continue button
        // otherwise we disable the continue button
        _guestList.value?.let {
            // if at least 1 checkbox is checked
            // you will try to complete these
            viewState.postValue(ViewState.EnableContinueButton(true))
            // else
            viewState.postValue(ViewState.EnableContinueButton(false))
        }
        // check if only reservation is checked, go to confirmation screen
        viewState.postValue(ViewState.ConfirmationScreen)
        // check if only need reservation is checked, show error snackbar
        viewState.postValue(ViewState.ErrorSnackbar)
        // check if mix of reservation and need reservation are checked, go to conflict screen
        viewState.postValue(ViewState.ConflictScreen)
    }
}

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val isCodeError: Boolean
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}

sealed class ViewState {
    data class EnableContinueButton(val enabled: Boolean) : ViewState()

    object ConfirmationScreen : ViewState()

    object ConflictScreen : ViewState()

    object ErrorSnackbar : ViewState()
}

