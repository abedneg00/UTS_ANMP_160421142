package com.example.hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp.model.Hobby
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HobbyViewModel(application: Application): AndroidViewModel(application) {
    val hobbyLD = MutableLiveData<ArrayList<Hobby>>()
    // WEEK 5
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        queue = Volley.newRequestQueue( getApplication() )
        val url = "http://10.0.2.2:8080/musics/musics.json"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {//ini jika volley sukses
                Log.d("show_volley", it)
                val sType = object : TypeToken<List<Hobby>>() { }.type
                val result = Gson().fromJson<List<Hobby>>(it, sType)
                hobbyLD.value = result as ArrayList<Hobby>?
            },
            {
                Log.e("show_volley", it.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest) // karena queue bisa null, kita harus tambahkan ?
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }


}


