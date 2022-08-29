package com.codinginflow.mvvm_guests.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/*
Guest Data Class for Reference:
data class Guest(
    val guestName: String,
    val hasReservation: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable
 */
@Dao
interface GuestDao {
    //-------------RETRIEVE DATA SECTION---------//
    @Query("SELECT * FROM guests_table")
    fun getGuests(): Flow<List<GuestRecyclerViewItem.Guest>>

    //-------------UPDATE SECTION----------------//
    //coroutine to insert guests to local db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(guest: GuestRecyclerViewItem.Guest)

    @Update
    suspend fun update(guest: GuestRecyclerViewItem.Guest)

    @Delete
    suspend fun delete(guest: GuestRecyclerViewItem.Guest)


}