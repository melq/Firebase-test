package com.melq.firebasetest.model.user

class User (
    var id:     String,
    var first:  String,
    var last:   String,
    var born:   Int
) {

    fun toHashMap(): HashMap<String, java.io.Serializable> {
        return hashMapOf(
            "id" to id,
            "first" to first,
            "last" to last,
            "born" to born
        )
    }

    override fun toString(): String {
        return hashMapOf(
            "id" to id,
            "first" to first,
            "last" to last,
            "born" to born
        ).toString()
    }
}