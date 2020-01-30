package com.student.dublin_assignment_kotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.student.dublin_assignment_kotlin.RV_WARN.RVW
import java.util.*
//(MD.ROKIBUL ISLAM
//STIDENT ID:18315)
class RV_WARN(var c: Context) : RecyclerView.Adapter<RVW>()
{
    var list: ArrayList<WARNING_MODEL>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVW {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.waen_list, parent, false)
        return RVW(v)
    }

    override fun onBindViewHolder(h: RVW, position: Int) {
        h.area.text = list[position].area
        h.level.text = list[position].level
        h.type.text = list[position].type
        // get the value from the array list and pass this intent to next screen
        h.ll.setOnClickListener {
            c.startActivity(
                Intent(c, Screen_3::class.java)
                    .putExtra("id", list[position].id)
                    .putExtra("a", list[position].area)
                    .putExtra("t", list[position].type)
                    .putExtra("l", list[position].level)
                    .putExtra("del", "yes")
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class RVW(itemView: View) : ViewHolder(itemView) {
        var area: TextView
        var level: TextView
        var type: TextView
        var ll: LinearLayout

        init {
            area = itemView.findViewById(R.id.cv_country)
            level = itemView.findViewById(R.id.cv_level)
            type = itemView.findViewById(R.id.cv_type)
            ll = itemView.findViewById(R.id.ll_warn)
        }
    }

    init {
        list = ArrayList()
        val res = Database(c).GET_WARNING()
        if (res.count == 0) {
        } else {
            while (res.moveToNext()) {
                val a = res.getString(res.getColumnIndex(Database.Area))
                val l = res.getString(res.getColumnIndex(Database.Level))
                val t = res.getString(res.getColumnIndex(Database.Type))
                val i = res.getString(res.getColumnIndex(Database.Id))
                list.add(WARNING_MODEL(a, l, t, i))
            }
        }
    }
}