package com.example.hobbyapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.hobbyapp.databinding.HobbyListItemBinding
import com.example.hobbyapp.model.Hobby
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class HobbyListAdapter(val hobbyList:ArrayList<Hobby>)
    :RecyclerView.Adapter<HobbyListAdapter.HobbyViewHolder>() { //isinya view holder
    class HobbyViewHolder(var binding: HobbyListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val binding = HobbyListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return HobbyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hobbyList.size
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        holder.binding.txtHobbyName.text = hobbyList[position].name_hobby
        holder.binding.txtCreator.text = hobbyList[position].name
        holder.binding.txtDescription.text = hobbyList[position].description
        val picasso = Picasso.Builder(holder.itemView.context)
        picasso.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(hobbyList[position].photoUrl).into(holder.binding.imageView, object:
            Callback {
            override fun onSuccess() {
                holder.binding.imageView.visibility = View.VISIBLE
            }
            override fun onError(e: Exception?) {
                Log.e("picasso_error", e.toString())
            }
        })

        holder.binding.btnRead.setOnClickListener{
            val id = hobbyList[position].id
            val action = HomeFragmentDirections.actionDetailFragment(Integer.parseInt(id))
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateHobbyList(newHobbyList: ArrayList<Hobby>) {
        hobbyList.clear()
        hobbyList.addAll(newHobbyList)
        notifyDataSetChanged()
    }
}
