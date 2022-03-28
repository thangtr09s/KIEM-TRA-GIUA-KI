package com.example.cruddemo

import java.util.*

data class UserModel(
    var id: Int = getAutoId(),
    var name: String = "",
    var email: String = "",
    var contact: String = "",
    var address: String = ""
) {
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(3000)
        }
    }
}