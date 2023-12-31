package com.example.movies.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.movies.DbHelper
import com.example.movies.R
import com.example.movies.Users

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
// switching activity

    var registerButtonBack = findViewById<Button>(R.id.registerButtonBack)
    registerButtonBack.setOnClickListener {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPass: EditText = findViewById(R.id.user_pass)
        val button: Button = findViewById(R.id.registerButtonEnd)

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login == "" || pass == "")
                Toast.makeText(this,"Fill in all the fields", Toast.LENGTH_LONG).show()
            else{
                val users = Users(login, pass)

                val db = DbHelper(this, null)
                db.addUser(users)
                Toast.makeText(this, "user $login is registered", Toast.LENGTH_LONG).show()

                userLogin.text.clear()
                userPass.text.clear()

            }
        }

    }
}