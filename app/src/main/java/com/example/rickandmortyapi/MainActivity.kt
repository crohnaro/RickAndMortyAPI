package com.example.rickandmortyapi

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nametextView = findViewById<TextView>(R.id.characterName)
        val headerImageView = findViewById<AppCompatImageView>(R.id.characterImage)
        val aliveTextView = findViewById<AppCompatTextView>(R.id.aliveStatus)
        val originTextView = findViewById<TextView>(R.id.characterOriginResult)
        val speciesTextView = findViewById<TextView>(R.id.characterSpeciesResult)



        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()


        val rickAndMortyService: RickandMortyService = retrofit.create(RickandMortyService::class.java)
        rickAndMortyService.getCharacaterById(10).enqueue(object : Callback<GenerateCharacterByIdResponse> {

            override fun onResponse(call: Call<GenerateCharacterByIdResponse>, response: Response<GenerateCharacterByIdResponse>) {
                Log.i("MainActiviy", response.toString())

                if(!response.isSuccessful){
                    Toast.makeText(this@MainActivity, "Unsucessful network get", Toast.LENGTH_LONG)
                    return
                }

                val body = response.body()!!
                nametextView.text = body.name
                aliveTextView.text = body.status
                originTextView.text = body.origin.name
                speciesTextView.text = body.species


            }


            override fun onFailure(call: Call<GenerateCharacterByIdResponse>, t: Throwable) {
                Log.i("MainActivity",t.message ?: "Null message" )
            }

        })


    }



}