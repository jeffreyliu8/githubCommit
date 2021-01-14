package com.regiscorp.myapplication

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.orhanobut.logger.Logger
import com.regiscorp.myapplication.constants.MIN_NUM_OF_COMMITS
import com.regiscorp.myapplication.constants.TARGET_GITHUB_OWNER
import com.regiscorp.myapplication.constants.TARGET_GITHUB_REPO
import com.regiscorp.myapplication.endpoint.WebEndpoint
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var webEndpoint: WebEndpoint

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.regiscorp.myapplication", appContext.packageName)
    }

    @Test
    internal fun testEndpointGetCommitsOver25() = runBlocking {
        try {
            val result =
                webEndpoint.getCommits(owner = TARGET_GITHUB_OWNER, repo = TARGET_GITHUB_REPO)
            if (result.isSuccessful) {
                assertTrue(result.body()?.size!! > MIN_NUM_OF_COMMITS)
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