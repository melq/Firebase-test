package com.melq.firebasetest.model.user

class User (val first: String, val last: String, val born: Int) {
    fun toHashMap(): HashMap<String, java.io.Serializable> {
        return hashMapOf(
            "first" to this.first,
            "last" to this.last,
            "born" to this.born
        )
    }
}