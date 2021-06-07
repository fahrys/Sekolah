package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.myapplication.ClassFragment
import com.example.myapplication.HomeFragment
import com.example.myapplication.AccountFragment
import com.example.myapplication.R
import com.example.myapplication.utils.FireBaseUtils.firebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import www.sanju.motiontoast.MotionToast

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

            /**
             * btn sign Out
             */
        textButton.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, SignIn::class.java))
            MotionToast.darkColorToast(this,"Success",
                "Selamat tinggal",
                MotionToast.TOAST_SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this,R.font.helvetica_regular))
            finish()
        }

         addFragment(HomeFragment.newInstance())
         bottom_navigasi.show(0)
         bottom_navigasi.add(MeowBottomNavigation.Model(0, R.drawable.ic_home))
         bottom_navigasi.add(MeowBottomNavigation.Model(1, R.drawable.ic_class))
         bottom_navigasi.add(MeowBottomNavigation.Model(2, R.drawable.ic_account))

         bottom_navigasi.setOnClickMenuListener {
            when (it.id) {
                0 -> {

                    replaceFragment(HomeFragment.newInstance())
                }
                1 -> {

                    replaceFragment(ClassFragment.newInstance())
                }
                2 -> {

                    replaceFragment(AccountFragment.newInstance())

                }
                else -> {
                    Toast.makeText(this, "404", Toast.LENGTH_SHORT).show()
                    replaceFragment(HomeFragment.newInstance())

                }
            }
         }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
            fragmentTransition.replace(R.id.Fragment_Container,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }
    private fun addFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
            fragmentTransition.add(R.id.Fragment_Container,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }
}