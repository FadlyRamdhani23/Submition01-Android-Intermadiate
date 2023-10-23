package org.d3if3127.submition1.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.d3if3127.submition1.databinding.ActivitySplashScreenBinding
import org.d3if3127.submition1.ui.factory.ViewModelFactory
import org.d3if3127.submition1.ui.main.MainActivity
import org.d3if3127.submition1.ui.main.MainViewModel
import org.d3if3127.submition1.ui.welcome.WelcomeActivity

class SplashScreenActivity : AppCompatActivity() {

        private lateinit var binding: ActivitySplashScreenBinding
        private val splashDisplayLength = 2000
        private val viewModel by viewModels<MainViewModel> {
            ViewModelFactory.getInstance(this)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivitySplashScreenBinding.inflate(layoutInflater)
            setContentView(binding.root)
            supportActionBar?.hide()


            viewModel.getSession().observe(this){ user ->
                if (user.isLogin){
                    Handler(Looper.getMainLooper()).postDelayed({
                        val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    }, splashDisplayLength.toLong())
                }else{
                    Handler(Looper.getMainLooper()).postDelayed({
                        val mainIntent = Intent(this@SplashScreenActivity, WelcomeActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    }, splashDisplayLength.toLong())

                }
            }
        }
    }
