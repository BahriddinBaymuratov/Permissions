package com.example.permission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.permission.model.Contact

class SendSmsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_sms)

        findViewById<TextView>(R.id.textView).text = intent.getParcelableExtra<Contact>("contact").toString()
    }
}