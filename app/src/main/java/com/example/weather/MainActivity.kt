package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //날씨 파싱 4
        //JSONArray : []
        //JsonObject : {}

        //Volley에 RequestQueue 생성하기
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)


        //API 주소 선언
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=Busan&appid=4b1404f09c6868209083b9d148a18b31" + "&language=ko-KR" + "&region=KR"


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
                    main_tv_temp.text = (temp - 273.15).toInt().toString()
                    main_tv_humidity.text = humidity.toString()
                    main_tv_speed.text = speed.toString()
                    main_tv_all.text = all.toString()


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
                    when (weatherId) {
                        in 200..232 -> main_iv_weather.setImageResource(R.drawable.stomy01)
                        in 300..321 -> main_iv_weather.setImageResource(R.drawable.rainy04)
                        in 500..531 -> main_iv_weather.setImageResource(R.drawable.rainy01)
                        in 600..622 -> main_iv_weather.setImageResource(R.drawable.snowy02)
                        in 701..781 -> main_iv_weather.setImageResource(R.drawable.cloudy02)
                        800 -> main_iv_weather.setImageResource(R.drawable.sunny02)
                        in 801..805 -> main_iv_weather.setImageResource(R.drawable.cloudy01)
                    }

                } catch (e: JSONException) {
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                   // Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                }
            }, Response.ErrorListener { //데이터가 정상적으로 호출되지 않았을 때 처리하는 부분
                    error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            })


        requestQueue.add(request)


    }


}