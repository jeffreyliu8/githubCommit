package com.regiscorp.myapplication.endpoint


import com.regiscorp.myapplication.model.CommitData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WebEndpoint {
    @GET("/repos/{owner}/{repo}/commits")
    suspend fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repo: String?,
    ): Response<List<CommitData>>
}
