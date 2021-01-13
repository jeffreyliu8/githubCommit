package com.regiscorp.myapplication.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.regiscorp.myapplication.model.CommitData
import com.regiscorp.myapplication.model.Resource
import com.regiscorp.myapplication.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _commitsLiveData = MediatorLiveData<Resource<List<CommitData>>>()
    val commitsLiveData: LiveData<Resource<List<CommitData>>>
        get() = _commitsLiveData

    fun getCommits(owner: String, repo: String) {
        viewModelScope.launch {
            repository.getCommits(owner, repo, _commitsLiveData)
        }
    }
}