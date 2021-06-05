package com.melq.firebasetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val _text: MutableLiveData<String> =
        MutableLiveData<String>().also { mutableLiveData ->
            mutableLiveData.value = "main"
        }
    val text: LiveData<String> get() = _text

    private val textList: MutableList<String> = mutableListOf<String>("alpha", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel")

    fun changeText() {
        _text.value = textList[Random.nextInt(8)]
    }
}