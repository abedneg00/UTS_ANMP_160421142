package com.example.hobbyapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val PREF_NAME = "user_session"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //untuk mengakses UI-UI yang ada
        super.onViewCreated(view, savedInstanceState)

        // Mengakses elemen UI dari layout LoginFragment
        val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNavigationView.visibility = View.GONE

        binding.btnSignIn.setOnClickListener{
            val usn = binding.txtUsername.text.toString()
            val pwd = binding.txtPassword.text.toString()
            if (usn != "" && pwd != ""){
                val url = "http://10.0.2.2:8080/musics/login.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener { response ->
                        if (response == "{\"status\":\"success\"}") {
                            Toast.makeText(context, "Welcome " + usn, Toast.LENGTH_SHORT).show()

                            saveUserSession(usn)

//                            val action = LoginFragmentDirections.actionHomeFragment()
//                            Navigation.findNavController(it).navigate(action)

                            bottomNavigationView.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener {
                        Toast.makeText(context, "Error: " + it.message, Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = usn
                        params["password"] = pwd
                        return params
                    }
                }
                Volley.newRequestQueue(context).add(stringRequest)
            }
            else{
                Toast.makeText(context, "Empty Username and Password", Toast.LENGTH_SHORT).show()
            }
        }

//        binding.btnSignUp.setOnClickListener{
//            val action = LoginFragmentDirections.actionSignUpFragment()
//            Navigation.findNavController(it).navigate(action)
//        }
    }

    private fun saveUserSession(username: String) {
        val sharedPref = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("username", username)
            apply()
        }
    }
}