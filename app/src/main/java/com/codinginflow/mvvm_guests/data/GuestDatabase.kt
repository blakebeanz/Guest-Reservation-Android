package com.codinginflow.mvvm_guests.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codinginflow.mvvm_guests.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

//not interface because we have to extend something
@Database(entities = [GuestRecyclerViewItem.Guest::class], version = 1)
abstract class GuestDatabase : RoomDatabase() {

    //to get dao where needed, use dependency injection
    abstract fun guestDao(): GuestDao

    class Callback@Inject constructor(
        private val database: Provider<GuestDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //do db operations
            val dao = database.get().guestDao()

            //launch coroutine and create dummy data
            applicationScope.launch {
                dao.insert(GuestRecyclerViewItem.Guest("Dale Gibson", true))
                dao.insert(GuestRecyclerViewItem.Guest("Jeremy Gibson", true))
                dao.insert(GuestRecyclerViewItem.Guest("Linda Gibson", false))
                dao.insert(GuestRecyclerViewItem.Guest("Margaret Gibson", false))
            }

        }
    }
}