package com.example.hobbyapp.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private val PREF_NAME = "user_session"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSignIn.setOnClickListener{
            val usn = binding.txtUsername.text.toString()
            val pwd = binding.txtPassword.text.toString()
            if (usn != "" && pwd != ""){
                val url = "http://10.0.2.2:8080/musics/login.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener { response ->
                        if (response == "{\"status\":\"success\"}") {
                            Toast.makeText(this, "Welcome $usn", Toast.LENGTH_SHORT).show()

                            saveUserSession(usn)

                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, "Error: " + it.message, Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = usn
                        params["password"] = pwd
                        return params
                    }
                }
                Volley.newRequestQueue(this).add(stringRequest)
            }
            else{
                Toast.makeText(this, "Empty Username and Password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveUserSession(username: String) {
        val sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("username", username)
            apply()
        }
    }
}