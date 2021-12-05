package com.malkinfo.vchat

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.malkinfo.vchat.adapter.UserAdapter
import com.malkinfo.vchat.databinding.ActivityMainBinding
import com.malkinfo.vchat.model.User


class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    var database: FirebaseDatabase? = null
    var users: ArrayList<User>? = null
    lateinit var usersAdapter: RecyclerView
    var dialog: ProgressDialog? = null
    var user: User? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        var Uid = FirebaseAuth.getInstance().uid!!
        var intr = intent.getStringExtra("intr").toString()
        dialog = ProgressDialog(this)
        dialog!!.setMessage("Uploading Image...")
        dialog!!.setCancelable(false)
        database = FirebaseDatabase.getInstance()
        users = ArrayList<User>()
        usersAdapter = findViewById(R.id.mRec)
        usersAdapter.layoutManager = GridLayoutManager(this@MainActivity, 2) //using grid layout

        if(FirebaseAuth.getInstance().currentUser == null){
            var intent= Intent(this@MainActivity, VerificationActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding!!.lout.setOnClickListener(View.OnClickListener {


            var intent = Intent(this@MainActivity, VerificationActivity::class.java) //logout
            startActivity(intent)
            finish()

                signout()

        })
        //calling method to fetch users
        getusr(intr)

        //on tapping user profile,start chat activity

        binding!!.chatlist.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, ChatlistActivity::class.java)
            startActivity(intent)

        })
        // interest tabs
        binding!!.t.setOnClickListener(View.OnClickListener{
            intr = "Technology"
            Toast.makeText(this, "your interest is Tech", Toast.LENGTH_SHORT).show()
                getusr(intr)
        })
        binding!!.s.setOnClickListener(View.OnClickListener{
            intr = "Sports"
            Toast.makeText(this, "your interest is sports", Toast.LENGTH_SHORT).show()
            getusr(intr)
        })
        binding!!.a.setOnClickListener(View.OnClickListener{
            intr = "Automobile"
            Toast.makeText(this, "your interest is Automobile", Toast.LENGTH_SHORT).show()
            getusr(intr)
        })



    }

    private fun getusr(intr: String) { //method to fetch users
        database!!.reference.child("users").child(intr)
            .addValueEventListener(object : ValueEventListener { //referring firebase realtime database
                override fun onDataChange(snapshot: DataSnapshot) {

                    user = snapshot.getValue(User::class.java)
                    //users!!.add(user!!)
                    usersAdapter.adapter = UserAdapter(this@MainActivity, users!!)

                }

                override fun onCancelled(error: DatabaseError) {}
            })

        database!!.reference.child("users").child(intr)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    users!!.clear()
                    for (snapshot1 in snapshot.children) {
                        val user: User? = snapshot1.getValue(User::class.java)
                        if (!user!!.uid.equals(FirebaseAuth.getInstance().uid)) users!!.add(user)
                    }

                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
    //method to sign out

    private fun signout() {

        FirebaseAuth.getInstance().signOut()
    }

    /*private fun loaddata(): String? {
        val sharedPreferences = getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY",null)
        return savedString
    }

    private fun savedata(intr: String) {
        val sharedPreferences = getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("STRING_KEY",intr)
        }.apply()
        Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show()
    }*/

    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("presence").child(currentId!!).setValue("Online")

    }

    override fun onPause() {
        super.onPause()
        /*val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("presence").child(currentId!!).setValue("Offline")
*/

    }



}