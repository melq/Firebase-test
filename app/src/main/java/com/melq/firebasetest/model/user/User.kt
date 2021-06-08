package com.melq.firebasetest.model.user

class User (
    val id:     String,
    val first:  String,
    val last:   String,
    val born:   Int
) {

    fun toHashMap(): HashMap<String, java.io.Serializable> {
        return hashMapOf(
            "id" to id,
            "first" to this.first,
            "last" to this.last,
            "born" to this.born
        )
    }

    override fun toString(): String {
        return hashMapOf(
            "id" to id,
            "first" to this.first,
            "last" to this.last,
            "born" to this.born
        ).toString()
    }
}