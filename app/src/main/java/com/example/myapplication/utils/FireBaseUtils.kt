package com.example.myapplication.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

// Di dalam objek ini, kami menginisialisasi FirebaseAuth dan nullable firebaseUser
object FireBaseUtils {
    val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser : FirebaseUser? = firebaseAuth.currentUser
}