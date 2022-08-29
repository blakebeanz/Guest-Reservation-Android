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
    /*private val _guestList = MutableLiveData<List<GuestRecyclerViewItem>>()
        guestList: LiveData<List<GuestRecyclerViewItem>>*/
            get() = _guestList

    init {
        getGuestListItems()
    }



    private fun getGuestListItems() = viewModelScope.launch {
        _guestList.postValue(com.codinginflow.mvvm_guests.ui.guests.Resource.Loading)
        //val guestList = guestDao.getGuests().asLiveData()


        //val guests = ArrayList(guestDao.getGuests().toList())
        val guests = guestDao.getGuests().first()

        //create recycler view
        val guestItemList = arrayListOf<GuestRecyclerViewItem>()
        guestItemList.add(GuestRecyclerViewItem.Title("These Guests Have a Reservation", 1))
        guestItemList.addAll(guests)
        guestItemList.add(GuestRecyclerViewItem.Title("These Guests Need a Reservation", 2))

        _guestList.postValue(com.codinginflow.mvvm_guests.ui.guests.Resource.Success<List<GuestRecyclerViewItem>>(value = guestItemList))
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

