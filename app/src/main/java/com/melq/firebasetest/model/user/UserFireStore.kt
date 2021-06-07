package com.melq.firebasetest.model.user

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFireStore {
    companion object {
        private const val TAG = "DocSnippets"
    }

    private val db = Firebase.firestore

    fun createUser(id: String, user: HashMap<String, java.io.Serializable>) {
        val collectionName = "users"
        val doc = db.collection(collectionName).document(id)

        doc.get()
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    Log.d(TAG, "DocumentSnapshot exists. data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")

                    doc.set(user)
                        .addOnSuccessListener { _ ->
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
}