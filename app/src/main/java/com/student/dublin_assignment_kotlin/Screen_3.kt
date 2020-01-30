package com.student.dublin_assignment_kotlin

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.util.*
//(MD.ROKIBUL ISLAM
//STIDENT ID:18315)
// this screen show the diffrent value to seclect the custom details and add in sqlite database
class Screen_3 : AppCompatActivity() {
    var s_area = ""
    var s_level = ""
    var s_type = ""
    var btn_save: Button? = null
    var btn_del: Button? = null
    var btn_close: Button? = null
    var btn_Add: Button? = null
    var del: String? = null
    var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_3)
        val db = Database(this)
        // Dropdown menu
        val spin_area = findViewById<Spinner>(R.id.spinner_area)
        val spin_leavel = findViewById<Spinner>(R.id.spinner_level)
        val spin_type = findViewById<Spinner>(R.id.spinner_type)
        type_list =
            Arrays.asList(*resources.getStringArray(R.array.type))
        level_list =
            Arrays.asList(*resources.getStringArray(R.array.level))
        btn_Add = findViewById(R.id.btn_add_warn)
        btn_del = findViewById(R.id.btn_delete_warn)
        btn_save = findViewById(R.id.btn_save_warn)
        btn_close = findViewById(R.id.btn_close_warn)
        // control the visible of button
        if (intent.getStringExtra("del") != null) {
            btn_Add!!.setVisibility(View.GONE)
            btn_del!!.setVisibility(View.VISIBLE)
            btn_save!!.setVisibility(View.VISIBLE)
            // intent value which pass from the RV_WARN
            id = intent.getStringExtra("id")
            s_area = intent.getStringExtra("a")
            s_level = intent.getStringExtra("l")
            s_type = intent.getStringExtra("t")

            val int_type = type_list!!.indexOf(s_type)
            val int_level = type_list!!.indexOf(s_level)

            spin_leavel.setSelection(int_level)
            spin_type.setSelection(int_type)
        } else {
            btn_Add!!.setVisibility(View.VISIBLE)
            btn_del!!.setVisibility(View.GONE)
            btn_save!!.setVisibility(View.GONE)
        }
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.area,
            android.R.layout.simple_spinner_item
        )
        // section of the level for warning
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_area.adapter = adapter
        val adapterl = ArrayAdapter.createFromResource(
            this,
            R.array.level,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_leavel.adapter = adapterl
        // dropdown list for type selection
        val adaptert = ArrayAdapter.createFromResource(
            this,
            R.array.type,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_type.adapter = adaptert
        spin_area.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                s_area = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        // after selection it is set to add in database
        spin_leavel.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                s_level = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        // on click type is set to add in database
        spin_type.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                s_type = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        findViewById<View>(R.id.btn_add_warn).setOnClickListener {
            val db = Database(applicationContext)
            val res = db.INSERT_NEW_USER_DATA(s_area, s_level, s_type)
            if (res) {
                finish()
            }
        }
        // delete on click current open value
        btn_del!!.setOnClickListener{
            val res = db.delete_ALL(id!!)
            if (res > 0) {
                finish()
            }
        }
        btn_save!!.setOnClickListener {
            val db = Database(applicationContext)
            val res = db.update_all(id!!, s_area, s_level, s_type)
            if (res > 0) {
                finish()
            }
        }
        // to go back to list screen
        btn_close!!.setOnClickListener { finish() }
    }

    companion object {
        var type_list: List<String>? = null
        var level_list: List<String>? = null
    }
}