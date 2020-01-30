package com.student.dublin_assignment_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//(MD.ROKIBUL ISLAM
//STIDENT ID:18315)
class Screen_2 : AppCompatActivity() {
    // used to show the list of warning where i add the custom warning for me as well
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_2)
        val button = findViewById<Button>(R.id.add)
        button.setOnClickListener {
            val intent = Intent(this@Screen_2, Screen_3::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // when this screen is ready this funtion call to get the refresh list
        GET_WARN()
    }

    fun GET_WARN() {
        val RV: RecyclerView = findViewById(R.id.RV_WARN)
        RV.setLayoutManager(LinearLayoutManager(this))
        val rv = RV_WARN(this)
        RV.setAdapter(rv)
    }
}