package com.example.hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.FragmentHomeBinding
import com.example.hobbyapp.viewmodel.HobbyViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel: HobbyViewModel
    private val hobbyListAdapter  = HobbyListAdapter(arrayListOf())
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HobbyViewModel::class.java)
        viewModel.refresh()

        //set layoutManager supaya bisa jalankan recycle view
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = hobbyListAdapter

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.hobbyLD.observe(viewLifecycleOwner, Observer {
            hobbyListAdapter.updateHobbyList(it)
        })

    }
}