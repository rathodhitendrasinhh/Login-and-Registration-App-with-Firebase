package com.example.loginandregistrationapp


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val TextEmail = findViewById<EditText>(R.id.emailinput)
        val ProgressBar = findViewById<ProgressBar>(R.id.progressBar)
        val TextPassword = findViewById<EditText>(R.id.pwdinput)
        val RegisterButton = findViewById<Button>(R.id.registerbtn)
        val loginButton = findViewById<TextView>(R.id.loginNow)

        RegisterButton.setOnClickListener {
            ProgressBar.setVisibility(View.VISIBLE)
            val password = TextPassword.text.toString()
            val email = TextEmail.text.toString()
            if (email.isBlank()) {
                Toast.makeText(this@Register, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isBlank()) {
                Toast.makeText(this@Register, "Please enter Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    ProgressBar.setVisibility(View.GONE)
                    if (task.isSuccessful) {
                        Toast.makeText(this@Register, "Registration Successful.", Toast.LENGTH_SHORT,).show()
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this@Register, "Authentication failed.", Toast.LENGTH_SHORT,).show()


                    }
                }

        }
        loginButton.setOnClickListener {
            val loginIntent = Intent(this, Login::class.java)
            startActivity(loginIntent)
            finish()

        }
    }
}