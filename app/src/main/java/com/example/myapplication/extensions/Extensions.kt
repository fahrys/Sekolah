package com.example.myapplication.extensions

import android.app.Activity
import android.widget.Toast
import com.ib.custom.toast.CustomToastView

object Extensions {
    fun Activity.toast(msg : String) {
        CustomToastView.makeText(this, Toast.LENGTH_LONG, CustomToastView.DEFAULT, msg, false ).show()
    }
}