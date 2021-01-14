package com.regiscorp.myapplication

import com.orhanobut.logger.Logger
import com.regiscorp.myapplication.constants.BASE_GITHUB_URL
import com.regiscorp.myapplication.constants.MIN_NUM_OF_COMMITS
import com.regiscorp.myapplication.constants.TARGET_GITHUB_OWNER
import com.regiscorp.myapplication.constants.TARGET_GITHUB_REPO
import com.regiscorp.myapplication.endpoint.WebEndpoint
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var webEndpoint: WebEndpoint

    @Before
    internal fun setUp() {
        val httpClient = OkHttpClient.Builder()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_GITHUB_URL)//unit test always against int
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        webEndpoint = retrofit.create(WebEndpoint::class.java)
    }

    @Test
    internal fun testEndpointGetCommitsOver25() = runBlocking {
        try {
            val result =
                webEndpoint.getCommits(owner = TARGET_GITHUB_OWNER, repo = TARGET_GITHUB_REPO)
            if (result.isSuccessful) {
                assertTrue(result.body()?.size!! > 0)
            } else {
                val errorResponse = result.errorBody()?.string()
                assertEquals("error response", errorResponse)
            }
        } catch (e: Exception) {
            Logger.e("error: " + e.localizedMessage)
            assertEquals("No network?", e.localizedMessage)
        }
    }
}