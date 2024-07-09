package com.monjoy.myimageapp.room.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monjoy.myimageapp.network.PhotoDetailRepository
import com.monjoy.myimageapp.room.entity.BreedDetail
import kotlinx.coroutines.launch

class PhotoDetailViewModel(private val repository: PhotoDetailRepository) : ViewModel()  {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _allBreeds = MutableLiveData<List<BreedDetail>>()
    val allBreeds: LiveData<List<BreedDetail>> = _allBreeds

    private val _breedPicUrl = MutableLiveData<String>()
    val breedPicUrl: LiveData<String> get() = _breedPicUrl

    suspend fun fetchBreeds() {
        _isLoading.value = true
        try {
            repository.fetchBreeds()
            _allBreeds.value = repository.getBreedsFromDb()
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            _isLoading.value = false
        }

    }


    fun fetchParticularBreed(breedName: String) {
        viewModelScope.launch {
            val picUrl = repository.fetchBreedDetail(breedName)
            _breedPicUrl.postValue(picUrl)
        }
    }




}

class PhotoDetailViewModelFactory(private val repository: PhotoDetailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}