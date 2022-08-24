package com.example.rickandmortyapi

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nametextView = findViewById<TextView>(R.id.characterName)
        val headerImageView = findViewById<AppCompatImageView>(R.id.characterImage)
        val aliveTextView = findViewById<AppCompatTextView>(R.id.aliveStatus)
        val originTextView = findViewById<TextView>(R.id.characterOriginResult)
        val speciesTextView = findViewById<TextView>(R.id.characterSpeciesResult)
        val genderImage = findViewById<AppCompatImageView>(R.id.characterGenderImage)

        viewModel.refreshCharacter(54)
        viewModel.characterByIdLiveData.observe(this){ response ->
            if (response == null){
                Toast.makeText(
                    this@MainActivity,
                    "Unucessfull network call",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }
            nametextView.text = response.name
            aliveTextView.text = response.status
            originTextView.text = response.origin.name
            speciesTextView.text = response.species

            if (response.gender.equals("male", true)){
                genderImage.setImageResource(R.drawable.ic_male_24)
            } else {
                genderImage.setImageResource(R.drawable.ic_female_24)
            }

            Picasso.get().load(response.image).into(headerImageView);
        }
    }



}