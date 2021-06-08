package com.melq.firebasetest.model.user

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFireStore {
    companion object {
        private const val TAG = "DocSnippets"
        const val collectionName = "users"
    }

    private val db = Firebase.firestore

    fun createUser(user: User) {
        val doc = db.collection(collectionName).document(user.id)
        doc.get()
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    Log.d(TAG, "DocumentSnapshot exists. data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                    doc.set(user)
                        .addOnSuccessListener {
                            Log.d(TAG, "Document created: $user")
                        }
                        .addOnFailureListener { e ->
                            Log.d(TAG, "create failed with", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "get failed with", e)
            }

        /*val user = hashMapOf( // IDをランダム文字列で割り振るとき
            "first" to "Bob",
            "last" to "Madison",
            "born" to 1988
        )
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }*/
     }

    fun getAllUser(): MutableList<User> {
        val userList: MutableList<User> = mutableListOf()
        db.collection(collectionName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    userList.add(document.data.toUser(document.id))
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error getting documents", e)
            }
        return userList
    }

    fun deleteUser(id: String) {
        db.collection(collectionName).document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
            }
    }

    private fun Map<String, Any>.toUser(id: String): User {
        val first = this["first"] as String
        val last = this["last"] as String
        val born = this["born"] as Int
        return User(id, first, last, born)
    }
}