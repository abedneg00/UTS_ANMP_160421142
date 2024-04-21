package com.example.hobbyapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val PREF_NAME = "user_session"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "")

        binding.txtUsername.setText(username)

        binding.btnSave.setOnClickListener {
            val usn = binding.txtUsername.text.toString()
            val first_name = binding.txtFirstName.text.toString()
            val last_name = binding.txtLastName.text.toString()
            val password = binding.txtPassword.text.toString()

            if (first_name != "" && last_name != "" && password != ""){
                val url = "http://10.0.2.2:8080/musics/update.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener { response ->
                        if (response == "{\"status\":\"success\"}") {
                            Toast.makeText(context, "Update Data Success!!!", Toast.LENGTH_SHORT).show()

//                            val action = SignUpFragmentDirections.actionLoginFragment()
//                            Navigation.findNavController(it).navigate(action)
                        }
                    },
                    Response.ErrorListener {
                        Toast.makeText(context, "Error: " + it.message, Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = usn
                        params["first_name"] = first_name
                        params["last_name"] = last_name
                        params["password"] = password
                        return params
                    }
                }
                Volley.newRequestQueue(context).add(stringRequest)

                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            else{
                Toast.makeText(context, "Empty Data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogOut.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}