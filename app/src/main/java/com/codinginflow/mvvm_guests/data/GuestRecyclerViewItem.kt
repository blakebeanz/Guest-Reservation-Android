package com.codinginflow.mvvm_guests.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
sealed class GuestRecyclerViewItem {
    @Entity(tableName = "guests_table")
    @Parcelize
    data class Guest(
        val guestName: String,
        val hasReservation: Boolean = false,
        @PrimaryKey(autoGenerate = true) val id: Int = 0
    ) : Parcelable, GuestRecyclerViewItem() {

    }

    data class Title(
        val title: String,
        val id: Int
    ) : GuestRecyclerViewItem(){

    }

    data class Info(
        val message: String,
        val id: Int
    ) : GuestRecyclerViewItem(){

    }
}
