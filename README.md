BACKGROUND:
For this project I created a Guest Reservation Application. The Project has 3 main screens: The select Guest
Screen, the conflict screen, and the confirmation screen. The "Continue" button is what allows you to move from 
the "select guests" screen to another screen. Guests are broken up based on if they currently have a reservation 
or if they need a reservation. The logic works like this:

-If no guests are selected via checkbox, do not make the continue box available; Wait there.
-If one or more guests are selected, make the continue button available
-sub-logic:
    -if all guests selected have a reservation and button is pressed, go to confirmation screen
    -if there is a mix of guests with reservations and without reservations and button is pressed, go to conflict screen
    -if all guests selected are without reservation and the button is pressed, display info snack bar 

MY BUILD:
-Room DB to store Guests
-Dependency Injection w/ Dagger Hilt
-MVVM Architecture
-Multiiple View-Type Recycler View
-Nav Graph with one main activity anf many fragments
-I've added 4 Guests (2 with reservations, 2 without) to the initial test case
-the RoomDB capabilities are tested in the test folder

Helpful Resources:
-using Room DB/Dependency Injection/MVVM - Playlist - https://www.youtube.com/watch?v=Udk6iaR-RXA&list=PLrnPJCHvNZuCfAe7QK2BoMPkv2TGM_b0E
-Dagger Hilt Documentation: https://dagger.dev/hilt/
-Creating RecyclerView w/ multiple view types: https://www.geeksforgeeks.org/recyclerview-multiple-view-types-in-android-with-example/
-Room DB Documentation: https://developer.android.com/reference/android/arch/persistence/room/RoomDatabase
-MVVM Reference: https://medium.com/nerd-for-tech/mvvm-architecture-in-android-using-retrofit-livedata-9ee1ad138d57
    


