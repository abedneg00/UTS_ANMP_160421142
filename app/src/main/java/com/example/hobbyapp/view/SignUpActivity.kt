package com.example.hobbyapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.ActivityLoginBinding
import com.example.hobbyapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSignUp.setOnClickListener{
            var username = binding.txtUsername.text.toString()
            var first_name = binding.txtFirstName.text.toString()
            var last_name = binding.txtLastName.text.toString()
            var email = binding.txtEmail.text.toString()
            var password = binding.txtPassword.text.toString()
            var rePassword = binding.txtRePassword.text.toString()

            if (password == rePassword && username.isNotBlank() && first_name.isNotBlank() && last_name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && rePassword.isNotBlank()){
                //apa
                val url = "http://10.0.2.2:8080/musics/signUp.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener { response ->
                        if (response == "{\"status\":\"success\"}") {
                            Toast.makeText(this, "SignUp Success!!!", Toast.LENGTH_SHORT).show()
//                            val action = SignUpFragmentDirections.actionLoginFragment()
//                            Navigation.findNavController(it).navigate(action)
                        } else {
                            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, "Error: " + it.message, Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = username
                        params["first_name"] = first_name
                        params["last_name"] = last_name
                        params["email"] = email
                        params["password"] = password
                        return params
                    }
                }
                Volley.newRequestQueue(this).add(stringRequest)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Empty or Mismatch Password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}