package com.melq.firebasetest.page.edit

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melq.firebasetest.model.user.User

class EditUserViewModel : ViewModel() {
    lateinit var user: User

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String> get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> get() = _lastName

    private val _born = MutableLiveData<Int>()
    val born: LiveData<Int> get() = _born

    fun putUserData(user: User) {
        this.user = user.apply {
            _userName.value = id
            _firstName.value = first
            _lastName.value = last
            _born.value = born
        }
    }
}