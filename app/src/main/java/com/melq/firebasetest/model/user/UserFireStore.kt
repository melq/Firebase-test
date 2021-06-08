package com.melq.firebasetest.model.user

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFireStore {
    companion object {
        const val collectionName = "users"
    }

    private val db = Firebase.firestore

    fun createUser(user: User) {
        val tag = "CREATE_USER"
        val doc = db.collection(collectionName).document(user.id)
        doc.get()
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    Log.d(tag, "DocumentSnapshot exists. data: ${document.data}")
                } else {
                    Log.d(tag, "No such document")
                    doc.set(user)
                        .addOnSuccessListener {
                            Log.d(tag, "Document created: $user")
                        }
                        .addOnFailureListener { e ->
                            Log.w(tag, "create failed with", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w(tag, "get failed with", e)
            }
     }

    fun getAllUser(): MutableList<User> {
        val tag = "GET_ALL_USER"
        val userList: MutableList<User> = mutableListOf()
        db.collection(collectionName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(tag, "${document.id} => ${document.data}")
                    userList.add(document.data.toUser())
                }
            }
            .addOnFailureListener { e ->
                Log.w(tag, "Error getting documents", e)
            }
        return userList
    }

    fun deleteUser(id: String) {
        val tag = "DELETE_USER"
        db.collection(collectionName).document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(tag, "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w(tag, "Error deleting document", e)
            }
    }

    fun editUser(user: User) {
        val tag = "EDIT_USER"
        db.collection(collectionName).document(user.id)
            .set(user)
            .addOnSuccessListener {
                Log.d(tag, "DocumentSnapshot successfully edited!")
            }
            .addOnFailureListener { e ->
                Log.w(tag, "Error editing document", e)
            }
    }

    private fun Map<String, Any>.toUser(): User {
        val id = this["id"] as String
        val first = this["first"] as String
        val last = this["last"] as String
        val born = this["born"] as Long
        return User(id, first, last, born.toInt())
    }
}