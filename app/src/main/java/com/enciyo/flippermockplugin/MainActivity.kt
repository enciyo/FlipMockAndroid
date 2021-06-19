package com.enciyo.flippermockplugin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.textView).setOnClickListener {

            GlobalScope.launch {
                try {
                    val data = Service.service.mock1("sadasdsa").string()
                    Log.i("MyLogger", data)
                }catch (e:Exception){
                    Log.i("MyLogger", e.message.toString())
                }
            }
        }
    }
}