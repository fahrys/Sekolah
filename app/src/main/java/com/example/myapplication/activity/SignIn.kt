package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.R
import com.example.myapplication.utils.FireBaseUtils.firebaseAuth
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.et_email
import kotlinx.android.synthetic.main.activity_sign_in.et_password
import www.sanju.motiontoast.MotionToast





class SignIn : AppCompatActivity() {

    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputArray: Array<TextInputLayout>
    lateinit var googleSignInClient: GoogleSignInClient

    val Req_Code: Int = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)





        btn_daftar.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        signInInputArray = arrayOf(et_email, et_password)
//        btn_registrasi.setOnClickListener {
//            startActivity(Intent(this, SignUp::class.java))
//            finish()
//        }

        btn_kirim.setOnClickListener {
            signInUser()

        }
    }


//--------------------------------------------Action btn--------------------------------------------------------------------------------//
//        btn_kirim.setOnClickListener {
//            val inputEmail: String = et_email.text.toString()
//            val inputPassword: String = et_password.text.toString()
//
//            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
//                CustomToastView.makeText(this, Toast.LENGTH_LONG, CustomToastView.INFO, "Plis Insert Email & Password", false).show()
//                return@setOnClickListener
//            }
//
//
//
//        }

    //---------------------------------------------Action Btn--------------------------------------------------------------------------------//

        fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

        fun signInUser() {

            signInEmail = et_email.editText?.text.toString().trim()
            signInPassword = et_password.editText?.text.toString().trim()

            if (notEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                        .addOnCompleteListener { signIn ->
                            if (signIn.isSuccessful) {
                                MotionToast.darkColorToast(this, "Success Login",
                                        "Yeyy Kamu Berhasil Masuk",
                                        MotionToast.TOAST_SUCCESS,
                                        MotionToast.GRAVITY_BOTTOM,
                                        MotionToast.LONG_DURATION,
                                        ResourcesCompat.getFont(this, R.font.helvetica_regular))

                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            } else {
                                MotionToast.darkColorToast(this, "Error Login",
                                        "Yahh, Coba Periksa Kembali Email & Password Kamu",
                                        MotionToast.TOAST_ERROR,
                                        MotionToast.GRAVITY_BOTTOM,
                                        MotionToast.LONG_DURATION,
                                        ResourcesCompat.getFont(this, R.font.helvetica_regular))

                            }
                        }
            } else {
                signInInputArray.forEach { input ->
                    if (input.editText?.text.toString().trim().isEmpty()) {
                        input.error = "${input.hint} tidak boleh kosong"
                    }
                }
            }
        }
}










