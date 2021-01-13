package com.regiscorp.myapplication.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.regiscorp.myapplication.R
import com.regiscorp.myapplication.constants.TARGET_GITHUB_OWNER
import com.regiscorp.myapplication.constants.TARGET_GITHUB_REPO
import com.regiscorp.myapplication.model.ResourceState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.commitsLiveData.observe(viewLifecycleOwner, { resource ->
            when (resource.state) {
                ResourceState.LOADING -> {
                    Logger.d("Loading")
                }
                ResourceState.SUCCESS -> {
                    Logger.d("loaded number of ${resource.data?.size}")
                }
                ResourceState.ERROR -> {
                    Logger.e("Error: ${resource.message}")
                }
            }
        })
        viewModel.getCommits(owner = TARGET_GITHUB_OWNER, repo = TARGET_GITHUB_REPO)
    }
}