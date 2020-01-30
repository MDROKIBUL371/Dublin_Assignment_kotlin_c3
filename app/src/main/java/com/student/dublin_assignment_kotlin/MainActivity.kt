package com.student.dublin_assignment_kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
//(MD.ROKIBUL ISLAM
//STIDENT ID:18315)
class MainActivity : AppCompatActivity() {

    //veribale setups
    var listView: ListView? = null
    var list : ArrayList<Week_MODEL?>? = null
    var day1: String? = null
    var day2: String? = null
    var day3: String? = null
    var day4: String? = null
    var day5: String? = null
    var day6: String? = null
    var day7: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images = intArrayOf(
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
        )
        listView = findViewById(R.id.list_of_days)

        val button = findViewById<Button>(R.id.my_warnings)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, Screen_2::class.java)
            startActivity(intent)
        }

        btn_refresh.setOnClickListener {
            request()
        }


        val Adapter: ArrayAdapter<String?> = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_list_item_1,
            cities
        )
        Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spin_city.setAdapter(Adapter)
        // selection of serach location
        spin_city.setOnItemSelectedListener(
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long)
                {
                    url = basePath+cities_co[i]
                    // again call to get data of locaiton is selected
                    request()
//                    request()
//                    request()
//                    request()
                    Toast.makeText(applicationContext, url, Toast.LENGTH_SHORT).show();
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        )
        request()
    }

    internal inner class MyAdapter(
        var mtitle: ArrayList<Week_MODEL?>,
        var title: Array<String?>) :
        ArrayAdapter<Week_MODEL?>(applicationContext, R.layout.rows_of_days, R.id.text, mtitle) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
        {
            val layoutInflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val row = layoutInflater.inflate(R.layout.rows_of_days, parent, false)
//            val imageView = row.findViewById<ImageView>(R.id.image)
            val icon = row.findViewById<TextView>(R.id.cv_daily_icon)
            val day = row.findViewById<TextView>(R.id.cv_daily_day)
            val temp_min = row.findViewById<TextView>(R.id.cv_daily_temp_min)
            val temp_max = row.findViewById<TextView>(R.id.cv_daily_temp_max)
            val wind = row.findViewById<TextView>(R.id.cv_daily_wind)
//            imageView.setImageResource(mimages[position])
            icon.text = mtitle.get(position)?.cloudy
            temp_max.text = mtitle.get(position)?.temperatureHigh
            temp_min.text = mtitle.get(position)?.temperatureLow
            wind.text = mtitle.get(position)?.windSpeed
            day.text = title.get(position)
            return row
        }

    }


    public fun request()
    {

        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_WEEK]
        when (day) {
            Calendar.SATURDAY -> {
                // etc.
                day1 = "saturday"
                day2 = "sunday"
                day3 = "monday"
                day4 = "tuesday"
                day5 = "wednesday"
                day6 = "thursday"
                day7 = "friday"
            }
            Calendar.SUNDAY -> {
                day1 = "sunday"
                day2 = "monday"
                day3 = "tuesday"
                day4 = "wednesday"
                day5 = "thursday"
                day6 = "friday"
                day7 = "saturday"
            }
            Calendar.MONDAY -> {
                day1 = "monday"
                day2 = "tuesday"
                day3 = "wednesday"
                day4 = "thursday"
                day5 = "friday"
                day6 = "saturday"
                day7 = "sunday"
            }
            Calendar.TUESDAY -> {
                day1 = "tuesday"
                day2 = "wednesday"
                day3 = "thursday"
                day4 = "friday"
                day5 = "saturday"
                day6 = "sunday"
                day7 = "monday"
            }
            Calendar.WEDNESDAY -> {
                day1 = "wednesday"
                day2 = "thursday"
                day3 = "friday"
                day4 = "saturday"
                day5 = "sunday"
                day6 = "monday"
                day7 = "tuesday"
            }
            Calendar.THURSDAY -> {
                day1 = "thursday"
                day2 = "friday"
                day3 = "saturday"
                day4 = "sunday"
                day5 = "monday"
                day6 = "tuesday"
                day7 = "wednesday"
            }
            Calendar.FRIDAY -> {
                day1 = "friday"
                day2 = "saturday"
                day3 = "sunday"
                day4 = "monday"
                day5 = "tuesday"
                day6 = "wednesday"
                day7 = "thursday"
            }
        }
        val title =
            arrayOf(day1, day2, day3, day4, day5, day6, day7,day1)


        list = ArrayList<Week_MODEL?>()
        list!!.clear();

        val mRequestQueue = Volley.newRequestQueue(this)
//        val basePath = "https://api.darksky.net/forecast/ebd6aa8663d7e74fed4daf56735d18f3/53.3498,-6.2603"


        //String Request initialized
        val mStringRequest = object : StringRequest(Request.Method.GET, url, Response.Listener { response ->
//            Toast.makeText(applicationContext, "Logged In Successfully", Toast.LENGTH_SHORT).show()


            try {
                val json_obejct = JSONObject(response)
                val daily_json = json_obejct.getString("daily")

                val json_daily_data = JSONObject(daily_json)
                val daily_data = json_daily_data.getString("data")

                val j = JSONArray(daily_data)
                for (i in 0 until j.length())
                {
//
                    val json_object = j.getJSONObject(i)
                    var cloudy = json_object.getString("icon")
                    var temperatureHigh = json_object.getString("temperatureHigh")
                    var temperatureLow = json_object.getString("temperatureLow")



                    var windSpeed = json_object.getString("windSpeed")


                    val d = Week_MODEL(cloudy,convert_f_to_c(temperatureHigh),convert_f_to_c(temperatureLow),windSpeed)

                    list!!.add(d)


                }

                val myAdapter = MyAdapter(list!!,title)
                listView!!.setAdapter(myAdapter)
//                val rrv = RV_dublin_adopter(list,this);
//
//                RV!!.adapter = rrv;

            } catch (e: JSONException) {
                e.printStackTrace()
                Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_LONG).show();
            }








        }, Response.ErrorListener { error ->
            Log.i("This is the error", "Error :" + error.toString())
            Toast.makeText(applicationContext, "Please make sure you enter correct password and username", Toast.LENGTH_SHORT).show()
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2.put("contract","dublin" )
                params2.put("apiKey", "feb54e2e8dd2f7c99631122b11957d78c0d510b5")
                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
            }

        }
        mRequestQueue!!.add(mStringRequest!!)

    }

    fun convert_f_to_c ( f :String) : String
    {
//        (32°F − 32) × 5/9
        var fd = java.lang.Double.parseDouble(f)

        var inside_brackets : Double = (fd -32) *(5.0/9.0)
        var final_ : Int = inside_brackets.roundToInt()

        return  final_.toString();



    }
    var basePath = "https://api.darksky.net/forecast/5fdc27d26d062a906a78156fcd053cd4/"
    var url = "";

    var cities = arrayOf("Dublin",
        "Cabinteely",
        "DalKey",
        "MonksTown",
        "Dundrum",
        "Clondalkin",
        "Blanchardstown",
        "Ballymun",
        "Donaghmede",
        "Howth"
    )
    var cities_co = arrayOf("53.3498,-6.2603",
        "52.263751,-6.159769",
        "52.273277,-6.105942",
        "52.290109,-6.155381",
        "52.290109,-6.247391",
        "53.316370,-6.396393",
        "53.391349,-6.397214",
        "53.395759,-6.282513",
        "53.398204,-6.161339",
        "53.374396,-6.052275"

    )
}