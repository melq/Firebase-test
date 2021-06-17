package com.melq.firebasetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melq.firebasetest.model.user.User
import com.melq.firebasetest.model.user.UserFireStore
import kotlinx.coroutines.*

class ActivityViewModel : ViewModel() {
    /* MainFragment用プロパティ */
    val isUserListLoaded = MutableLiveData<Boolean>()
    var userList: MutableList<User> = mutableListOf()

    /* CreateUserFragment */
    val errorMessage = MutableLiveData<String>()
    val done = MutableLiveData<Boolean>()

    /* EditUserFragment */
    lateinit var user: User
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String> get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> get() = _lastName

    private val _born = MutableLiveData<Int>()
    val born: LiveData<Int> get() = _born


    fun loadUserList() {
        val tmpList = UserFireStore().getAllUser()

        viewModelScope.launch { // 待つ処理をメインスレッドで行うと、取得が中断されてしまう
            while (tmpList.isEmpty()) { delay(100) }
            userList.run {
                clear()
                addAll(tmpList)
            }
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

    fun deleteUser(id: String) {

    }

    fun putUserData(user: User) {
        this.user = user.apply {
            _userName.value = id
            _firstName.value = first
            _lastName.value = last
            _born.value = born
        }
    }
}