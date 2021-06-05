package com.melq.firebasetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _text by lazy { MutableLiveData<String>("main") }
    val text: LiveData<String> get() = _text

    fun changeText(str: String) { _text.value = str }
}