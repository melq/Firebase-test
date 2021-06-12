package com.melq.firebasetest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melq.firebasetest.model.user.User
import com.melq.firebasetest.model.user.UserFireStore
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    val isUserListLoaded = MutableLiveData<Boolean>()
    var userList: MutableList<User> = mutableListOf()

    fun loadUserList() {
        val tmpList = UserFireStore().getAllUser()

        viewModelScope.launch { // 待つ処理をメインスレッドで行うと、取得が中断されてしまう
            while (tmpList.isEmpty()) { delay(100) }
            userList.clear()
            userList.addAll(tmpList)
            isUserListLoaded.value = true
        }

        /*runBlocking { // これだと更新できない(getAllUserで取得が完了する前に関数が終わってる？)
            val tmpList: MutableList<User> = mutableListOf()
            launch {
                tmpList.addAll(UserFireStore().getAllUser())
            }.join() // joinで待たれていないっぽい
            userList.clear()
            userList.addAll(tmpList)
            isUserListLoaded.value = true
            Log.d("LOAD_LIST", "$tmpList")
        }*/
    }
}