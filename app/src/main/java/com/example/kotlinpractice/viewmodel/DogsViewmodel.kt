package com.example.kotlinpractice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinpractice.models.DogModel
import com.example.kotlinpractice.network.NetworkResponse
import com.example.kotlinpractice.repo.DogsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DogsViewmodel : ViewModel() {
    var dogsRepository: DogsRepository = DogsRepository()
    var dogsResultsData: MutableLiveData<DogModel> = MutableLiveData()
    var showLoader: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getDogData()
    }

    fun getDogData() = viewModelScope.launch(Dispatchers.IO) {
        showLoader.postValue(true)
        when (val result: NetworkResponse<DogModel> = dogsRepository.getDogData()) {
            is NetworkResponse.Success -> {
                showLoader.postValue(false)
       if(result.body?.status.equals("success"))
       {
           dogsResultsData.postValue(result.body!!)
       }
            }

            is NetworkResponse.Error -> {
                showLoader.postValue(false)
            }
        }
    }
}