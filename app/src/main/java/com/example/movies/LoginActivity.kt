package com.example.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //navigation between screens
        var registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
//navigation between screens

        val userLogin: EditText = findViewById(R.id.login_auth)
        val userPass: EditText = findViewById(R.id.pass_auth)
        val button: Button = findViewById(R.id.loginButton)

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login == "" || pass == "")
                Toast.makeText(this,"Fill in all the fields", Toast.LENGTH_LONG).show()
            else{
                val db = DbHelper(this, null)
                val isAuth = db.getUser(login, pass)

                if (isAuth){
                    Toast.makeText(this, "user $login is authorized", Toast.LENGTH_LONG).show()
                    userLogin.text.clear()
                    userPass.text.clear()

                }else{
                    Toast.makeText(this, "user $login isn't authorized", Toast.LENGTH_LONG).show()
                }

            }
        }

    }
}