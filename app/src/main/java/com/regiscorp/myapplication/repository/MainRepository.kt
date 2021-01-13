package com.regiscorp.myapplication.repository

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.regiscorp.myapplication.endpoint.WebEndpoint
import com.regiscorp.myapplication.extension.setError
import com.regiscorp.myapplication.extension.setLoading
import com.regiscorp.myapplication.extension.setSuccess
import com.regiscorp.myapplication.model.CommitData
import com.regiscorp.myapplication.model.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(private val webEndpoint: WebEndpoint) {
    suspend fun getCommits(
        owner: String,
        repo: String,
        liveData: MutableLiveData<Resource<List<CommitData>>>
    ) {
        Logger.d("loading")
        liveData.setLoading()

        try {
            val result = webEndpoint.getCommits(owner = owner, repo = repo)
            if (result.isSuccessful) {
                liveData.setSuccess(result.body())
            } else {
                val errorResponse = result.errorBody()?.string()
                liveData.setError(errorResponse)
            }
        } catch (e: Exception) {
            Logger.e("error: " + e.localizedMessage)
            liveData.setError(e.localizedMessage)
        }
    }
}