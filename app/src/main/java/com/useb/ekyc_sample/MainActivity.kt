package com.useb.ekyc_sample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.useb.ekyc_sample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.runButton.setOnClickListener {
            val secondIntent = Intent(applicationContext, WebViewActivity::class.java)
            if (sendDataToWebview(secondIntent)) startActivity(secondIntent)
        }
    }

    private fun sendDataToWebview(secondIntent: Intent): Boolean {
        val yearStr = binding!!.year.text.toString()
        val monthStr = binding!!.month.text.toString()
        val dayStr = binding!!.day.text.toString()
        val birthday: String
        birthday =
            if (yearStr.length == 0 || monthStr.length == 0 || dayStr.length == 0) "" else "$yearStr-$monthStr-$dayStr"
        val name = binding!!.name.text.toString()
        val phoneNumber = binding!!.phoneNumber.text.toString()
        val email = binding!!.email.text.toString()
        return if (isValid(email, name, phoneNumber, birthday)) {
            secondIntent.putExtra("birthday", birthday)
            secondIntent.putExtra("name", name)
            secondIntent.putExtra("phoneNumber", phoneNumber)
            secondIntent.putExtra("email", email)
            true
        } else {
            false
        }
    }

    private fun isValid(
        email: String,
        name: String,
        phoneNumber: String,
        birthday: String
    ): Boolean {
        val inputValidator = InputValidator()
        var allowFlag = true
        if (inputValidator.isNullOrEmpty(email) || inputValidator.isNullOrEmpty(name) || inputValidator.isNullOrEmpty(
                phoneNumber
            ) || inputValidator.isNullOrEmpty(birthday)
        ) {
            Toast.makeText(this@MainActivity, "??? ?????? ?????? ???????????????", Toast.LENGTH_SHORT).show()
            allowFlag = false
        } else if (!inputValidator.isValidEmail(email)) {
            Toast.makeText(this@MainActivity, "????????? ????????? ?????? ????????????.", Toast.LENGTH_SHORT).show()
            allowFlag = false
        }
        return allowFlag
    }
}