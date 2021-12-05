package com.malkinfo.vchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.malkinfo.vchat.adapter.ChatlistAdapter
import com.malkinfo.vchat.databinding.ActivityChatlistBinding
import com.malkinfo.vchat.model.Usr
import com.google.firebase.storage.FirebaseStorage
 //Chatlist Activity

class   ChatlistActivity : AppCompatActivity() {

    private lateinit var db :Query
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var chatArrayList: ArrayList<Usr>
    private var Uid:String? = null
    var binding: ActivityChatlistBinding?= null
    var storage: FirebaseStorage? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatlist)
        userRecyclerView = findViewById(R.id.chatlist)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        chatArrayList = arrayListOf<Usr>()
        storage = FirebaseStorage.getInstance()
        Uid = FirebaseAuth.getInstance().uid

            getusrdata()


    }
 //Method to get users list to whomever the person has chatted to

    private fun getusrdata() {


        db = FirebaseDatabase.getInstance().getReference("chats").orderByChild("uid").equalTo(Uid!!) //getting list from database


        db.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (usersnapshot in snapshot.children){
                        val usrname = usersnapshot.getValue(Usr::class.java)
                        chatArrayList.add(usrname!!)
                    }
                    userRecyclerView.adapter = ChatlistAdapter(this@ChatlistActivity,chatArrayList) //passing data to adapter

                }


            }


            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}