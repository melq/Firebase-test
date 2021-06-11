package com.melq.firebasetest.page.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melq.firebasetest.model.user.User
import com.melq.firebasetest.model.user.UserFireStore
import kotlinx.coroutines.launch

class CreateUserViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val done = MutableLiveData<Boolean>()

    fun createUser(id: String, first: String, last: String, born: String) {
        if (id.isNotEmpty() &&
            first.isNotEmpty() &&
            last.isNotEmpty()&&
            born.isNotEmpty()
        ) {
            viewModelScope.launch {
                UserFireStore().createUser(User(id, first, last, born.toInt()))
                done.value = true
            }
        } else {
            errorMessage.value = "Please input all"
            return
        }

    }
}