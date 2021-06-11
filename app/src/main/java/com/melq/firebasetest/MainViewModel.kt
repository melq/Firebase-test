package com.melq.firebasetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melq.firebasetest.model.user.User
import com.melq.firebasetest.model.user.UserFireStore

class MainViewModel : ViewModel() {
    val userList: MutableList<User> = UserFireStore().getAllUser() // LiveDataにしたほうがいい？
}