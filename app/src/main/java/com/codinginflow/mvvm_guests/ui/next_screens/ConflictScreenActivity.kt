package com.codinginflow.mvvm_guests.ui.next_screens

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import mvvm_guests.databinding.ActivityConfictScreenBinding


class ConflictScreenActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityConfictScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityConfictScreenBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)
    }

    override fun onStart() {
        super.onStart()

    }
}