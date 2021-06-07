package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.R
import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FireBaseUtils.firebaseAuth
import com.example.myapplication.utils.FireBaseUtils.firebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ib.custom.toast.CustomToastView
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.et_email
import kotlinx.android.synthetic.main.activity_sign_up.et_password
import www.sanju.motiontoast.MotionToast

class SignUp : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    lateinit var userName: String
    lateinit var userEmail: String
    lateinit var userPhone: String
    lateinit var userPassword: String
    lateinit var createAccountInputArray: Array<EditText>

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        auth = FirebaseAuth.getInstance()



        btn_sign_in.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }

        createAccountInputArray =
            arrayOf(et_username, et_email, et_phone, et_password, et_Confrm_password)

        btn_registrasi.setOnClickListener {
            signIn()
        }

    }

    /* check if there's a signed-in user*/
    override fun onStart() {
        super.onStart()

        // periksa apakah pengguna saat ini sudah login atau belum:
        // Check if user is signed in (non-null) and update UI accordingly.
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, HomeActivity::class.java))
            toast("Welcome Back")
        }
    }


    private fun notEmpety(): Boolean = et_username.text.toString().trim().isNotEmpty() &&
            et_email.text.toString().trim().isNotEmpty() &&
            et_phone.text.toString().trim().isNotEmpty() &&
            et_password.text.toString().trim().isNotEmpty() &&
            et_Confrm_password.text.toString().trim().isNotEmpty()


    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpety() &&
            et_password.text.toString().trim() == et_Confrm_password.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpety()) {
            createAccountInputArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is Required"
                }
            }
        } else {
            MotionToast.darkColorToast(this,"PERINGATAN!",
                "Password Tidak Sama",
                MotionToast.TOAST_WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this,R.font.helvetica_regular))
        }
        return identical
    }


    private fun signIn() {
        if (identicalPassword()) {
            // identicalPassword() returns true only  when inputs are not empty and passwords are identical
            userName = et_username.text.toString().trim()
            userEmail = et_email.text.toString().trim()
            userPhone = et_phone.text.toString().trim()
            userPassword = et_password.text.toString().trim()

            /*create a user*/
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        MotionToast.darkColorToast(this,"SUCCESS!",
                            "Yeyy, Kamu Berhasil Daftar, Login Dulu",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(this,R.font.helvetica_regular))

                        val user: FirebaseUser? = auth.currentUser
                        val userID: String = user!!.uid

                        // Buat database FireBase
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID)

                        val hashMap: HashMap<String, String> = HashMap()
                        hashMap.put("UserID", userID)
                        hashMap.put("UserName", userName)
                        hashMap.put("Email", userEmail)
                        hashMap.put("Phone", userPhone)
                        hashMap.put("ProfileImage", "")

                        databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                            if (task.isSuccessful) {
                                sendEmailVerification()
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            }
                        }

                    } else {
                        CustomToastView.makeText(this, Toast.LENGTH_LONG, CustomToastView.ERROR, "failed to Authenticate !", false).show()
                    }
                }

        }
    }


    /* send verification email to the new user. This will only
    *  work if the firebase user is not null.
    */

    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    CustomToastView.makeText(this, Toast.LENGTH_LONG, CustomToastView.INFO, "email sent to $userEmail", false).show()
                }
            }
        }
    }








    
}