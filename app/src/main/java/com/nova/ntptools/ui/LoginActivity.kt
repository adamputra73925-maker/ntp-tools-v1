package com.nova.ntptools.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nova.ntptools.R
import com.nova.ntptools.utils.PreferencesHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var preferencesHelper: PreferencesHelper

    // Credentials
    private val VALID_USERNAME = "nova"
    private val VALID_PASSWORD = "free"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preferencesHelper = PreferencesHelper(this)

        // Check if already logged in
        if (preferencesHelper.isLoggedIn()) {
            navigateToMain()
            return
        }

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
    }

    private fun setupListeners() {
        loginButton.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val username = usernameInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username dan password harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        if (username == VALID_USERNAME && password == VALID_PASSWORD) {
            preferencesHelper.setLoggedIn(true)
            preferencesHelper.setUsername(username)
            navigateToMain()
        } else {
            Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show()
            passwordInput.text.clear()
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
