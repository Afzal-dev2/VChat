package com.malkinfo.vchat

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.malkinfo.vchat.databinding.ActivitySetupProfileBinding
import com.malkinfo.vchat.model.User
import java.util.*


class SetupProfileActivity : AppCompatActivity() {
    var binding: ActivitySetupProfileBinding? = null
    var auth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var storage: FirebaseStorage? = null
    var selectedImage: Uri? = null
    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        dialog = ProgressDialog(this)
        dialog!!.setMessage("Updating profile...")
        dialog!!.setCancelable(false)
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        binding!!.imageView.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 45)
        })
        var intrst:String = ""
        binding!!.btn1.setOnClickListener(View.OnClickListener{
            intrst = "Technology"
            Toast.makeText(this, "your interest is Tech", Toast.LENGTH_SHORT).show()

        })
        binding!!.btn2.setOnClickListener(View.OnClickListener{
            intrst = "Sports"
            Toast.makeText(this, "your interest is sports", Toast.LENGTH_SHORT).show()
        })
        binding!!.btn3.setOnClickListener(View.OnClickListener{
            intrst = "Automobile"
            Toast.makeText(this, "your interest is Automobile", Toast.LENGTH_SHORT).show()
        })
        binding!!.continueBtn02.setOnClickListener(View.OnClickListener {
            val name: String = binding!!.nameBox.getText().toString()

            if (name.isEmpty()) {
                binding!!.nameBox.setError("Please type a name")
                return@OnClickListener
            }
            dialog!!.show()
            if (intrst==""){
                Toast.makeText(this, "Please choose your interest", Toast.LENGTH_SHORT).show()
            }

            if (selectedImage != null) {
                val reference = storage!!.reference.child("Profiles").child(
                        auth!!.uid!!
                )
                reference.putFile(selectedImage!!).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        reference.downloadUrl.addOnSuccessListener { uri ->
                            val imageUrl = uri.toString()
                            val uid = auth!!.uid
                            val intr = intrst
                            var notnull = "Notnull"
                            val phone = auth!!.currentUser!!.phoneNumber
                            val name: String = binding!!.nameBox.getText().toString()
                            val user = User(uid,intr,name, phone, imageUrl)
                            database!!.reference
                                    .child("users")
                                    .child(intr)
                                    .child(uid!!)
                                    .setValue(user)
                                    .addOnSuccessListener {
                                        dialog!!.dismiss()
                                        val intent = Intent(
                                                this@SetupProfileActivity,
                                                MainActivity::class.java
                                        )
                                        intent.putExtra("intr",intr)
                                        intent.putExtra("notnull",notnull)
                                        startActivity(intent)
                                        finish()
                                    }
                        }
                    }

                }
            } else {
                val intr = intrst
                var notnull = "Notnull"
                val uid = auth!!.uid
                val phone = auth!!.currentUser!!.phoneNumber
                val user = User(uid,intr, name, phone, "No Image")
                database!!.reference
                        .child("users")
                        .child(intr)
                        .child(uid!!)
                        .setValue(user)
                        .addOnSuccessListener {
                            dialog!!.dismiss()
                            val intent = Intent(this@SetupProfileActivity, MainActivity::class.java)
                            intent.putExtra("intr",intr)
                            intent.putExtra("notnull",notnull)
                            startActivity(intent)
                            finish()
                        }
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.data != null) {
                val uri = data.data // filepath
                val storage = FirebaseStorage.getInstance()
                val time = Date().time
                val reference = storage.reference.child("Profiles").child(time.toString() + "")
                reference.putFile(uri!!).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        reference.downloadUrl.addOnSuccessListener { uri ->
                            val filePath = uri.toString()
                            val obj = HashMap<String, Any>()
                            obj["image"] = filePath
                            database!!.reference.child("users")
                                    .child(FirebaseAuth.getInstance().uid!!)
                                    .updateChildren(obj).addOnSuccessListener { }
                        }
                    }
                }
                binding!!.imageView.setImageURI(data.data)
                selectedImage = data.data
            }
        }
    }
    /*private fun savedata2(intr: String) {
        val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("STRING_KEY",intr)
        }.apply()
        Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show()
    }*/
}