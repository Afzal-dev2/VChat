package com.malkinfo.vchat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.malkinfo.vchat.R
import com.bumptech.glide.Glide
import com.malkinfo.vchat.model.Usr
import de.hdodenhof.circleimageview.CircleImageView
import android.content.Context
import android.content.Intent
import com.malkinfo.vchat.ChatActivity

//Adapter for chatlist

class ChatlistAdapter(var context: Context, private val chatlist: ArrayList<Usr>):RecyclerView.Adapter<ChatlistAdapter.ChatViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val a = LayoutInflater.from(context).inflate(R.layout.usritem, parent,false)
        return ChatViewHolder(a)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val name = chatlist[position]
        holder.usrname.text = name.name
        holder.lastMsg.text = name.lastMsg
        Glide.with(context).load(name.profimg)
            .placeholder(R.drawable.avatar)
            .into(holder.profileimg)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name",name.name)
            intent.putExtra("image",name.profimg)
            intent.putExtra("uid",name.ruid)
            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return chatlist.size
    }

    class ChatViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        //going from Chatlist Activity to Chat Activity when any profile is clicked

        var usrname : TextView = itemView.findViewById(R.id.usrname)
        var profileimg : CircleImageView = itemView.findViewById(R.id.prof)
        var lastMsg : TextView= itemView.findViewById(R.id.lastmsgs)
    }


}