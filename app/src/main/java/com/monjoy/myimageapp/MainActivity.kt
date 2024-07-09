package com.monjoy.myimageapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.monjoy.feedapp.utils.NetworkUtils
import com.monjoy.myimageapp.adapter.BreedAdapter
import com.monjoy.myimageapp.databinding.ActivityMainBinding
import com.monjoy.myimageapp.interfaces.OnItemClickListener
import com.monjoy.myimageapp.network.PhotoDetailRepository
import com.monjoy.myimageapp.retrofit.ApiService
import com.monjoy.myimageapp.retrofit.RetrofitClient
import com.monjoy.myimageapp.room.BreedDatabase
import com.monjoy.myimageapp.room.entity.BreedDetail
import com.monjoy.myimageapp.room.viewmodel.PhotoDetailViewModel
import com.monjoy.myimageapp.room.viewmodel.PhotoDetailViewModelFactory
import com.monjoy.myimageapp.utils.CommonUtils
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var photoDetailViewModel: PhotoDetailViewModel
    private lateinit var apiService: ApiService
    private lateinit var onItemClickListener: OnItemClickListener
    private var breedAdapter: BreedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = BreedDatabase.getDatabase(this)!!
        apiService = RetrofitClient.createService(ApiService::class.java)
        val repository = PhotoDetailRepository(apiService, db.mPhotoDetailDao())
        val viewModelFactory = PhotoDetailViewModelFactory(repository)
        photoDetailViewModel = ViewModelProvider(this, viewModelFactory)[PhotoDetailViewModel::class.java]
        onItemClickListener = this
        binding.progressCircular.visibility = View.GONE

        lifecycleScope.launch {
            if(NetworkUtils.isNetworkConnected(this@MainActivity)){
                photoDetailViewModel.fetchBreeds()
            }else{
                showNoInternetSnackbar()
            }
        }

        photoDetailViewModel.isLoading.observe(this, Observer { isLoading ->
            if(NetworkUtils.isNetworkConnected(this@MainActivity)){
                if (isLoading) {
                    binding.progressCircular.visibility = View.VISIBLE
                } else {
                    binding.progressCircular.visibility = View.GONE
                }
            }else{
                CommonUtils.alertDialog(this@MainActivity, resources.getString(R.string.check_internet_connection))
            }

        })

        photoDetailViewModel.allBreeds.observe(this) { breedList ->
            // Update your UI here with the observed posts
            setRecyclerView(breedList)
        }
    }

    private fun setRecyclerView(breedList: List<BreedDetail>) {
        if(breedList.isEmpty()){
            binding.txtNoData.visibility = View.VISIBLE
            binding.recyclerBreed.visibility = View.GONE
        }else{
            binding.txtNoData.visibility = View.GONE
            binding.recyclerBreed.visibility = View.VISIBLE
            breedAdapter = BreedAdapter(breedList, onItemClickListener)
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.recyclerBreed.layoutManager = layoutManager
            binding.recyclerBreed.adapter = breedAdapter
        }

    }


    override fun onItemClick(breedDetail: BreedDetail?) {
        super.onItemClick(breedDetail)
        openDetailsPage(breedDetail)
    }

    private fun openDetailsPage(breedDetail: BreedDetail?) {
        var picUrl = ""
        lifecycleScope.launch {
            if(NetworkUtils.isNetworkConnected(this@MainActivity)){
                photoDetailViewModel.fetchParticularBreed(breedDetail?.breedName.toString())

            }else{
                CommonUtils.alertDialog(this@MainActivity, resources.getString(R.string.check_internet_connection))
            }
        }

        photoDetailViewModel.breedPicUrl.observe(this, Observer { url ->
            url?.let {
                picUrl = it
            }

        })

        if(picUrl.isNotEmpty()){
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra("photo_details", picUrl)
            startActivity(intent)
        }

    }


    private fun showNoInternetSnackbar() {
        val snackbar = Snackbar.make(binding.root, getString(R.string.check_internet_connection), Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Retry") {
            lifecycleScope.launch {
                if(NetworkUtils.isNetworkConnected(this@MainActivity)){
                    photoDetailViewModel.fetchBreeds()
                }else{
                    showNoInternetSnackbar()
                }
            }
        }
        snackbar.show()
    }

}