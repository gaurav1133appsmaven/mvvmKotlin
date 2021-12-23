package com.example.kotlinpractice.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinpractice.viewmodel.DogsViewmodel
import com.example.kotlinpractice.databinding.LayoutDogBinding
import com.squareup.picasso.Picasso

class ShowMeDog : AppCompatActivity() {
    lateinit var viewmodel: DogsViewmodel
    lateinit var binding: LayoutDogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutDogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel = ViewModelProvider(this).get(DogsViewmodel::class.java)
        binding.apply {
            button.setOnClickListener {
                viewmodel.getDogData()
            }
            progressBar.visibility = View.VISIBLE

        }
        observers()
    }

    private fun observers() {
        observeDogsLive()
        observeLoader()
    }

    private fun observeLoader() {
        viewmodel.showLoader.observe(this,
            {

                if (it) binding.progressBar.visibility = View.VISIBLE
                else binding.progressBar.visibility = View.GONE
            })
    }

    private fun observeDogsLive() {

        viewmodel.dogsResultsData.observe(this, { response ->
            Picasso.get().load(response.message).into(binding.imageView)
        })
    }
}