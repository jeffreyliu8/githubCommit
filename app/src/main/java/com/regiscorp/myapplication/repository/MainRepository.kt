package com.regiscorp.myapplication.repository

import com.regiscorp.myapplication.endpoint.WebEndpoint
import com.regiscorp.myapplication.model.CommitData
import com.regiscorp.myapplication.other.Resource


import javax.inject.Inject

class MainRepository @Inject constructor(private val webEndpoint: WebEndpoint) {
    suspend fun getCommits(
        owner: String,
        repo: String,
    ): Resource<List<CommitData>> {
        return try {
            val response = webEndpoint.getCommits(owner = owner, repo = repo)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.success(it)
                } ?: Resource.error("Error: result is null")
            } else {
                Resource.error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection")
        }
    }
}