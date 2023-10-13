package org.d3if3127.submition1.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import id.vee.android.ui.signup.SignupViewModel
import kotlinx.coroutines.launch
import org.d3if3127.submition1.R
import org.d3if3127.submition1.databinding.ActivitySignUpBinding
import org.d3if3127.submition1.ui.ViewModelFactory
import org.d3if3127.submition1.ui.login.LoginActivity
import org.d3if3127.submition1.ui.login.LoginViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        playAnimation()
        actionSet()
        buttonSet()

    }
    private suspend fun performRegistration(name: String, email: String, password: String) {
        try {
            val response = viewModel.register(name, email, password)
            Log.d("Registration", "Registration successful: $response")
            runOnUiThread {
                showSuccessDialog(email)
                showLoading(false)
            }
        } catch (e: Exception) {
            Log.e("Registration", "Registration failed: $e")
            runOnUiThread {
                showLoading(false)
            }
        }
    }
    private fun setToolbar() {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameText = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val name = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailText = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordText = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val password = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(title, nameText, name, emailText,emailTextLayout, passwordText, password, signup)
            start()
        }

    }

    private fun buttonSet(){
        val email = binding.emailEditText.text.toString()
        binding.signupButton.isEnabled = true && email.isNotEmpty() && binding.passwordEditText.text.toString().length >= 8
    }
    private fun actionSet(){
        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                buttonSet()
                if (s.toString().length < 2) {
                    binding.nameEditText.error = "Nama tidak boleh kosong"
                } else {
                    binding.passwordEditTextLayout.error = null
                }

            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    binding.passwordEditTextLayout.error = "Password minimal 8 karakter"
                } else {
                    binding.passwordEditTextLayout.error = null
                }
                buttonSet()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length < 2) {
                    binding.emailEditText.error = "Email harus diisi"
                } else {
                    binding.emailEditText.error = null
                }
                buttonSet()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            showLoading(true)
            lifecycleScope.launch {
                performRegistration(name, email, password)
            }
        }
    }
    private fun showSuccessDialog(email: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
            setPositiveButton("Lanjut") { _, _ ->
                finish()
            }
            create()
            show()
        }
    }
    private fun showLoading(isLoading: Boolean){
        binding.loadingProgressBar.isVisible = isLoading
    }
}