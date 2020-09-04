package com.example.weather

import android.content.Context
import android.content.Intent

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException


/*
Weater ID 대분류
2XX : 천둥번개 (비 유무 다 포함) ->stomy01
3xx :  가랑비 ->rainy04
5xxx : 비 (폭우 포함) ->rainy01
6xx : 눈 ->snowy01
7xx : 안개 ->cloudy02
800 : 맑음 ->sunny02
8xx : 구름 ->cloudy01
*/


class MainActivity() : AppCompatActivity() {

    var city : String = "Pohang"


    override fun onCreate(savedInstanceState: Bundle? ){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val ab = supportActionBar
        supportActionBar?.title = "액션바"


        //날씨 파싱 4
        //JSONArray : []
        //JsonObject : {}

        //Volley에 RequestQueue 생성하기
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)


        val list = mutableListOf<String>()
        list.add("Pohang")
        list.add("Seoul")
        list.add("Gumi")
        list.add("Daejeon")
        list.add("Busan")
        list.add("Yongin")
        list.add("Yangsu-ri")
        list.add("Changwon")

        city = list.random()

        if(intent.getStringExtra("check") != null){
            city = list.random()

        }
        //API 주소 선언
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=4b1404f09c6868209083b9d148a18b31"


        //API를 호출함

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {//데이터가 정상적으로 호출됐을 때 처리하는 부분
                //받아온 json 데이터를 Toast message로 출력
                    response ->
                try {
                    val weatherId: Int =
                        response.getJSONArray("weather").getJSONObject(0).getInt("id")
                    val description: String =
                        response.getJSONArray("weather").getJSONObject(0).getString("description")
                    val temp: Double = response.getJSONObject("main").getDouble("temp")
                    val humidity: Int = response.getJSONObject("main").getInt("humidity")
                    val speed: Double = response.getJSONObject("wind").getDouble("speed")
                    val all: Int = response.getJSONObject("clouds").getInt("all")
                    val name: String = response.getString("name")

                    //Double형을 textView의 텍스트로 적용할 때, 자릿수가 너무 길어질 때
                    // : Double형 -> Int형


                    main_tv_city.text = name
                    main_tv_description.text = description
                    main_tv_temp.text = (temp - 273.15).toInt().toString() + "℃"
                    main_tv_humidity.text = humidity.toString() + "%"
                    main_tv_speed.text = speed.toString() + "km/h"
                    main_tv_all.text = all.toString() + "%"


/*
Weater ID 대분류
2XX : 천둥번개 (비 유무 다 포함) ->stomy01
3xx :  가랑비 ->rainy04
5xx : 비 (폭우 포함) ->rainy01
6xx : 눈 ->snowy01
7xx : 안개 ->cloudy02
800 : 맑음 ->sunny02
8xx : 구름 ->cloudy01
*/
                    val sunnyRandom = (1..4).random()
                    val snowyRandom = (1..3).random()
                    val rainyRandom = (2..5).random()
                    val stormRandom = (1..5).random()
                    when (weatherId) {
                        in 200..232 -> main_iv_weather.setImageResource(R.drawable.stomy01)
                        in 300..321 ->
                            when (stormRandom) {

                                1 -> main_iv_weather.setImageResource(R.drawable.rainy01)
                                2 -> main_iv_weather.setImageResource(R.drawable.rainy02)
                                3 -> main_iv_weather.setImageResource(R.drawable.rainy03)
                                4 -> main_iv_weather.setImageResource(R.drawable.rainy04)
                                5 -> main_iv_weather.setImageResource(R.drawable.rainy05)
                                else -> main_iv_weather.setImageResource(R.drawable.cloudy01)
                            }
                        in 500..531 ->
                            when (rainyRandom) {
                                2 -> main_iv_weather.setImageResource(R.drawable.rainy02)
                                3 -> main_iv_weather.setImageResource(R.drawable.rainy03)
                                4 -> main_iv_weather.setImageResource(R.drawable.rainy04)
                                5 -> main_iv_weather.setImageResource(R.drawable.rainy05)
                                else -> main_iv_weather.setImageResource(R.drawable.cloudy01)
                            }
                        in 600..622 ->
                            when (snowyRandom) {
                                1 -> main_iv_weather.setImageResource(R.drawable.snowy01)
                                2 -> main_iv_weather.setImageResource(R.drawable.snowy02)
                                3 -> main_iv_weather.setImageResource(R.drawable.snowy03)
                                else -> main_iv_weather.setImageResource(R.drawable.sunny01)
                            }
                        in 701..781 -> main_iv_weather.setImageResource(R.drawable.cloudy02)
                        800 ->
                            when (sunnyRandom) {
                                1 -> main_iv_weather.setImageResource(R.drawable.sunny01)
                                2 -> main_iv_weather.setImageResource(R.drawable.sunny02)
                                3 -> main_iv_weather.setImageResource(R.drawable.sunny03)
                                4 -> main_iv_weather.setImageResource(R.drawable.sunny04)
                                else -> main_iv_weather.setImageResource(R.drawable.snowy01)
                            }

                        in 801..805 ->
                            when (snowyRandom) {
                                1 -> main_iv_weather.setImageResource(R.drawable.cloudy01)
                                2 -> main_iv_weather.setImageResource(R.drawable.cloudy02)
                                3 -> main_iv_weather.setImageResource(R.drawable.cloudy03)
                                else -> main_iv_weather.setImageResource(R.drawable.rainy01)
                            }




                    }





                    refresh_layout.setOnRefreshListener{

                        var requestQueue: RequestQueue = Volley.newRequestQueue(this)


                        val list = mutableListOf<String>()
                        list.add("Pohang")
                        list.add("Seoul")
                        list.add("Gumi")
                        list.add("Daejeon")
                        list.add("Busan")
                        list.add("Yongin")
                        list.add("Yangsu-ri")
                        list.add("Changwon")


                        city = list.random()

                        if(intent.getStringExtra("check") != null){
                            city = list.random()

                        }
                        //API 주소 선언
                        val url =
                            "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=4b1404f09c6868209083b9d148a18b31"


                        //API를 호출함

                        val request = JsonObjectRequest(Request.Method.GET, url, null,
                            Response.Listener {//데이터가 정상적으로 호출됐을 때 처리하는 부분
                                //받아온 json 데이터를 Toast message로 출력
                                    response ->
                                try {
                                    val weatherId: Int =
                                        response.getJSONArray("weather").getJSONObject(0)
                                            .getInt("id")
                                    val description: String =
                                        response.getJSONArray("weather").getJSONObject(0)
                                            .getString("description")
                                    val temp: Double =
                                        response.getJSONObject("main").getDouble("temp")
                                    val humidity: Int =
                                        response.getJSONObject("main").getInt("humidity")
                                    val speed: Double =
                                        response.getJSONObject("wind").getDouble("speed")
                                    val all: Int = response.getJSONObject("clouds").getInt("all")
                                    val name: String = response.getString("name")

                                    //Double형을 textView의 텍스트로 적용할 때, 자릿수가 너무 길어질 때
                                    // : Double형 -> Int형


                                    main_tv_city.text = name
                                    main_tv_description.text = description
                                    main_tv_temp.text = (temp - 273.15).toInt().toString() + "℃"
                                    main_tv_humidity.text = humidity.toString() + "%"
                                    main_tv_speed.text = speed.toString() + "km/h"
                                    main_tv_all.text = all.toString() + "%"


/*
Weater ID 대분류
2XX : 천둥번개 (비 유무 다 포함) ->stomy01
3xx :  가랑비 ->rainy04
5xx : 비 (폭우 포함) ->rainy01
6xx : 눈 ->snowy01
7xx : 안개 ->cloudy02
800 : 맑음 ->sunny02
8xx : 구름 ->cloudy01
*/
                                    val sunnyRandom = (1..4).random()
                                    val snowyRandom = (1..3).random()
                                    val rainyRandom = (2..5).random()
                                    val stormRandom = (1..5).random()
                                    when (weatherId) {
                                        in 200..232 -> main_iv_weather.setImageResource(R.drawable.stomy01)
                                        in 300..321 ->
                                            when (stormRandom) {

                                                1 -> main_iv_weather.setImageResource(R.drawable.rainy01)
                                                2 -> main_iv_weather.setImageResource(R.drawable.rainy02)
                                                3 -> main_iv_weather.setImageResource(R.drawable.rainy03)
                                                4 -> main_iv_weather.setImageResource(R.drawable.rainy04)
                                                5 -> main_iv_weather.setImageResource(R.drawable.rainy05)
                                                else -> main_iv_weather.setImageResource(R.drawable.cloudy01)
                                            }
                                        in 500..531 ->
                                            when (rainyRandom) {
                                                2 -> main_iv_weather.setImageResource(R.drawable.rainy02)
                                                3 -> main_iv_weather.setImageResource(R.drawable.rainy03)
                                                4 -> main_iv_weather.setImageResource(R.drawable.rainy04)
                                                5 -> main_iv_weather.setImageResource(R.drawable.rainy05)
                                                else -> main_iv_weather.setImageResource(R.drawable.cloudy01)
                                            }
                                        in 600..622 ->
                                            when (snowyRandom) {
                                                1 -> main_iv_weather.setImageResource(R.drawable.snowy01)
                                                2 -> main_iv_weather.setImageResource(R.drawable.snowy02)
                                                3 -> main_iv_weather.setImageResource(R.drawable.snowy03)
                                                else -> main_iv_weather.setImageResource(R.drawable.sunny01)
                                            }
                                        in 701..781 -> main_iv_weather.setImageResource(R.drawable.cloudy02)
                                        800 ->
                                            when (sunnyRandom) {
                                                1 -> main_iv_weather.setImageResource(R.drawable.sunny01)
                                                2 -> main_iv_weather.setImageResource(R.drawable.sunny02)
                                                3 -> main_iv_weather.setImageResource(R.drawable.sunny03)
                                                4 -> main_iv_weather.setImageResource(R.drawable.sunny04)
                                                else -> main_iv_weather.setImageResource(R.drawable.snowy01)
                                            }

                                        in 801..805 ->
                                            when (snowyRandom) {
                                                1 -> main_iv_weather.setImageResource(R.drawable.cloudy01)
                                                2 -> main_iv_weather.setImageResource(R.drawable.cloudy02)
                                                3 -> main_iv_weather.setImageResource(R.drawable.cloudy03)
                                                else -> main_iv_weather.setImageResource(R.drawable.rainy01)
                                            }


                                    }
                                }  catch (e: JSONException) {
                                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()


                                    // Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                                }
                            }, Response.ErrorListener { //데이터가 정상적으로 호출되지 않았을 때 처리하는 부분
                                    error ->
                                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
                                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                            })


                        requestQueue.add(request)

                        refresh_layout.isRefreshing = false




                    }

                    main_ibtn_search.setOnClickListener {

                        val intent = Intent(this, CityListActivity::class.java)
                        intent.putExtra("city_list",city)
                    }



                    main_ibtn_search.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://n.weather.naver.com"))
                        startActivity(intent)

                    }


                } catch (e: JSONException) {
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()


                    // Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                }
            }, Response.ErrorListener { //데이터가 정상적으로 호출되지 않았을 때 처리하는 부분
                    error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            })


        requestQueue.add(request)




    }



}


