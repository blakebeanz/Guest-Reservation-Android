package com.codinginflow.mvvm_guests.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class GuestDaoTest {

    private lateinit var database: GuestDatabase
    private lateinit var dao: GuestDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GuestDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.guestDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertGuestItem() = runBlockingTest {
        val guestItem1 = GuestRecyclerViewItem.Guest("Dale Gibson", true)
        dao.insert(guestItem1)

        val guests = dao.getGuests().first()

        assertThat(guests).contains(guestItem1)
    }

    @Test
    fun insertGuestItems() = runBlockingTest {
        val guestItem1 = GuestRecyclerViewItem.Guest("Dale Gibson", true)
        val guestItem2 = GuestRecyclerViewItem.Guest("Ben Gibson", false)
        val guestItem3 = GuestRecyclerViewItem.Guest("Jim Gibson", true)
        val guestItem4 = GuestRecyclerViewItem.Guest("Margaret Gibson", false)

        dao.insert(guestItem1)
        dao.insert(guestItem2)
        dao.insert(guestItem3)
        dao.insert(guestItem4)

        val guests = dao.getGuests().first() //return is a flow -> first() get's the list

        assertThat(guests).contains(guestItem1)
        assertThat(guests).contains(guestItem2)
        assertThat(guests).contains(guestItem3)
        assertThat(guests).contains(guestItem4)
    }

}