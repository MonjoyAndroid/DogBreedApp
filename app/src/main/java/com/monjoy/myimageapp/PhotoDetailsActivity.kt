package com.monjoy.myimageapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson
import com.monjoy.myimageapp.databinding.ActivityPhotoDetailsBinding
import com.monjoy.myimageapp.network.PhotoDetailRepository
import com.monjoy.myimageapp.retrofit.ApiService
import com.monjoy.myimageapp.retrofit.RetrofitClient
import com.monjoy.myimageapp.room.BreedDatabase
import com.monjoy.myimageapp.room.viewmodel.PhotoDetailViewModel
import com.monjoy.myimageapp.room.viewmodel.PhotoDetailViewModelFactory

class PhotoDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailsBinding
    private lateinit var photoDetailViewModel: PhotoDetailViewModel
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = BreedDatabase.getDatabase(this)!!
        apiService = RetrofitClient.createService(ApiService::class.java)
        val repository = PhotoDetailRepository(apiService, db.mPhotoDetailDao())
        val viewModelFactory = PhotoDetailViewModelFactory(repository)
        photoDetailViewModel = ViewModelProvider(this, viewModelFactory)[PhotoDetailViewModel::class.java]
        setDetailsView()
    }


    @SuppressLint("SetTextI18n")
    private fun setDetailsView() {
        val photourl = intent.getStringExtra("photo_details")
        photourl?.let {
            Glide.with(this)
                .load(photourl)
                .placeholder(R.drawable.icon_placeholder) // Placeholder image resource
                .error(R.drawable.icon_error_image_load) // Error image resource
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imgFeedItem)
        }

        binding.imgArrowBack.setOnClickListener {
            onBackPressed()
        }


    }



    override fun onBackPressed() {
        super.onBackPressed()
    }
}