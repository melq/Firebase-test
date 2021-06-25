package com.melq.firebasetest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.melq.firebasetest.model.user.User
import com.melq.firebasetest.model.user.UserFireStore
import kotlinx.coroutines.*

class ActivityViewModel : ViewModel() {
    private val repository = UserFireStore()

    /* MainFragment用プロパティ */
    val isUserListLoaded = MutableLiveData<Boolean>()
    var userList: MutableList<User> = mutableListOf()

    /* CreateUserFragment */
    val eMessage = MutableLiveData<String>()
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

    /*認証周り*/
    val auth = Firebase.auth
    var isLogin = false


    fun loadUserList() {
        repository.getAllUser() { tmpList ->
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

    fun createUser(id: String, first: String, last: String, born: String) { // vm.user使えば引数いらなくできる
        if (id.isEmpty() ||
            first.isEmpty() ||
            last.isEmpty() ||
            born.isEmpty()
        ) {
            eMessage.value = "Please input all"
            return
        } else {
            viewModelScope.launch {
                repository.createUser(User(id, first, last, born.toInt()))
                done.value = true
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            repository.deleteUser(user.id)
            done.value = true
        }
    }

    fun editUser() {
        if (user.id.isEmpty() ||
            user.first.isEmpty() ||
            user.last.isEmpty() ||
            user.born == Int.MIN_VALUE
        ) {
            eMessage.value = "Please input all"
            return
        } else {
            viewModelScope.launch {
                repository.editUser(user)
                done.value = true
            }
        }
    }

    fun updateInnerUserData() {
        this.user.run {
            _userName.value = id
            _firstName.value = first
            _lastName.value = last
            _born.value = born
        }
    }

    fun loginPushed(email: String, password: String) {
        val tag = "LOGIN"
        if (email.isBlank() || password.isBlank()) {
            eMessage.value = "必要な情報を入力してください"
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(tag, "signInWithEmail: success")
                    eMessage.value = "$email としてログインしました"
                    done.value = true
                } else {
                    when (task.exception) {
                        is FirebaseNetworkException -> {
                            Log.w(tag, "signInWithEmail: failure", task.exception)
                            eMessage.value = "ネットワークエラー"
                        }
                        is FirebaseAuthInvalidUserException -> {
                            Log.w(tag, "signInWithEmail: failure", task.exception)
                            eMessage.value = "登録されていないメールアドレスです"
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Log.w(tag, "signInWithEmail: failure", task.exception)
                            eMessage.value = "メールアドレスまたはパスワードが正しくありません"
                        }
                        else -> {
                            Log.w(tag, "signInWithEmail: failure", task.exception)
                            eMessage.value = "エラーが発生しました"
                        }
                    }
                }
            }
    }

    fun createPushed(email: String, password: String) {
        val tag = "CREATE_ACCOUNT"
        if (email.isBlank() || password.isBlank()) {
            eMessage.value = "必要な情報を入力してください"
            return
        }
        if (password.length < 6) {
            eMessage.value = "パスワードは6文字以上にしてください"
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(tag, "createUserWithEmail: success")
                    done.value = true
                } else {
                    when (task.exception) {
                        is FirebaseNetworkException -> {
                            Log.w(tag, "signInWithEmail: failure", task.exception)
                            eMessage.value = "ネットワークエラー"
                        }
                        is FirebaseAuthUserCollisionException -> {
                            Log.w(tag, "signInWithEmail: failure", task.exception)
                            eMessage.value = "登録済みのメールアドレスです"
                        }
                        else -> {
                            Log.w(tag, "signInWithEmail: failure", task.exception)
                            eMessage.value = "エラーが発生しました"
                        }
                    }
                }
            }
    }
}