package com.malkinfo.vchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.malkinfo.vchat.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {

    var binding : ActivityVerificationBinding? = null

    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance() //Starting Main Activity after successful verification
        if (auth!!.currentUser != null){
            val intent = Intent(this@VerificationActivity,
                MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        supportActionBar?.hide()
        binding!!.editNumber.requestFocus()
        binding!!.continueBtn.setOnClickListener {
            val intent = Intent(this@VerificationActivity, //Going back to previous activity if verification fails
                OTPActivity::class.java)
            intent.putExtra("phoneNumber",binding!!.editNumber.text.toString())
            startActivity(intent)
        }

    }
}