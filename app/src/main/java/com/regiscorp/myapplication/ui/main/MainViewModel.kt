package com.regiscorp.myapplication.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.regiscorp.myapplication.model.CommitData
import com.regiscorp.myapplication.other.Resource

import com.regiscorp.myapplication.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _commitsLiveData = MutableLiveData<Resource<List<CommitData>>>()
    val commitsLiveData: LiveData<Resource<List<CommitData>>>
        get() = _commitsLiveData

    fun getCommits(owner: String, repo: String) {
        viewModelScope.launch {
            _commitsLiveData.value = Resource.loading()
            val response = repository.getCommits(owner, repo)
            _commitsLiveData.value = response
        }
    }
}