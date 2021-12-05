//Adapter to fetch Users

package com.malkinfo.vchat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.malkinfo.vchat.ChatActivity
import com.malkinfo.vchat.R
import com.malkinfo.vchat.databinding.ItemProfileBinding
import com.malkinfo.vchat.model.User

class UserAdapter(var context:Context,var userList:ArrayList<User>):
RecyclerView.Adapter<UserAdapter.UserViewHolder>()

{
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding :ItemProfileBinding = ItemProfileBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        //Setting view

       var v = LayoutInflater.from(context).inflate(R.layout.item_profile,
               parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user = userList[position]
        holder.binding.username.text = user.name
        Glide.with(context).load(user.profileImage)
                .placeholder(R.drawable.avatar)
                .into(holder.binding.profile)
        holder.itemView.setOnClickListener {
            //going from Main Activity to Chat Activity when any profile is clicked

            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name",user.name)
            intent.putExtra("image",user.profileImage)
            intent.putExtra("uid",user.uid)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int  = userList.size
}