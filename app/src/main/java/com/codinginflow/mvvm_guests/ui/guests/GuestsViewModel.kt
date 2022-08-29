package com.codinginflow.mvvm_guests.ui.guests

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.codinginflow.mvvm_guests.data.GuestDao
import com.codinginflow.mvvm_guests.data.GuestRecyclerViewItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.annotation.Resource

class GuestViewModel @ViewModelInject constructor(private val guestDao: GuestDao) : ViewModel() {
    private val _guestList = MutableLiveData<com.codinginflow.mvvm_guests.ui.guests.Resource<List<GuestRecyclerViewItem>>>()
    val guestList: LiveData<com.codinginflow.mvvm_guests.ui.guests.Resource<List<GuestRecyclerViewItem>>>
            get() = _guestList

    init {
        getGuestListItems()
    }



    private fun getGuestListItems() = viewModelScope.launch {
        _guestList.postValue(com.codinginflow.mvvm_guests.ui.guests.Resource.Loading)
        //val guestList = guestDao.getGuests().asLiveData()


        //val guests =guestDao.getGuests().toCollection()
        val guests = guestDao.getGuests().first()


        //create recycler view
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

        _guestList.postValue(com.codinginflow.mvvm_guests.ui.guests.Resource.Success(guestItemList))
    }
}
sealed class Resource<out T> {
    data class Success<out T>(val value: T) : com.codinginflow.mvvm_guests.ui.guests.Resource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val isCodeError: Boolean
    ) : com.codinginflow.mvvm_guests.ui.guests.Resource<Nothing>()

    object Loading : com.codinginflow.mvvm_guests.ui.guests.Resource<Nothing>()
}

