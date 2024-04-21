package com.example.hobbyapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hobbyapp.databinding.FragmentHobbyDetailBinding
import com.example.hobbyapp.viewmodel.HobbyViewModel
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class HobbyDetailFragment : Fragment() {
    private lateinit var viewModel: HobbyViewModel
    private lateinit var binding:FragmentHobbyDetailBinding

    var multiPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHobbyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HobbyViewModel::class.java)
        viewModel.refresh()

        observeViewModel()

        if(arguments!=null){
            val id = HobbyDetailFragmentArgs.fromBundle(requireArguments()).id
            binding.txtCreator.text = id.toString()
        }
//        arguments?.let {
//            val id = HobbyDetailFragmentArgs.fromBundle(it).id
//            binding.txtCreator.text = id.toString()
//        }
    }

    fun observeViewModel() {
        //bertujuan untuk mendengarkan dari live data. jika data baru muncul, maka UI akan menanggapi
        viewModel.hobbyLD.observe(viewLifecycleOwner, Observer {hobbies->
            var length = hobbies.size

            displayData()

            if(multiPage == 0){
                binding.btnPrev.isEnabled = false
                binding.btnNext.isEnabled = true
            }

            binding.btnNext.setOnClickListener {
                multiPage+=1
                displayData()
                if(multiPage == 0){
                    binding.btnPrev.isEnabled = false
                    binding.btnNext.isEnabled = true
                }
                else if(multiPage+1 == length){
                    binding.btnPrev.isEnabled = true
                    binding.btnNext.isEnabled = false
                }
                else{
                    binding.btnPrev.isEnabled = true
                    binding.btnNext.isEnabled = true
                }
            }

            binding.btnPrev.setOnClickListener {
                multiPage-=1
                displayData()
                if(multiPage == 0){
                    binding.btnPrev.isEnabled = false
                    binding.btnNext.isEnabled = true
                }
                else if(multiPage-1 == length){
                    binding.btnPrev.isEnabled = true
                    binding.btnNext.isEnabled = false
                }
            }

//            val length = hobbies.size
//
//            displayData()
//
//            binding.btnPrev.isEnabled = multiPage > 0
//            binding.btnNext.isEnabled = multiPage < length - 1
//
//            binding.btnNext.setOnClickListener {
//                multiPage++
//                displayData()
//            }
//
//            binding.btnPrev.setOnClickListener {
//                multiPage--
//                displayData()
//            }
        })
    }

    fun displayData(){
        Picasso.get().load(viewModel.hobbyLD.value?.get(multiPage)?.photoUrl).into(binding.imageView)
        binding.txtHobbyName.text = viewModel.hobbyLD.value?.get(multiPage)?.name_hobby
        binding.txtCreator.text = viewModel.hobbyLD.value?.get(multiPage)?.name
        binding.txtAdditional.text = viewModel.hobbyLD.value?.get(multiPage)?.additional
    }

//    private fun displayData() {
//        val hobby = viewModel.hobbyLD.value?.get(multiPage)
//        hobby?.let {
//            Picasso.get().load(it.photoUrl).into(binding.imageView)
//            binding.txtHobbyName.text = it.name_hobby
//            binding.txtCreator.text = it.name
//            binding.txtAdditional.text = it.additional
//        }
//    }
}