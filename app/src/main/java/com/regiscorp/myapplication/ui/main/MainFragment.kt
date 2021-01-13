package com.regiscorp.myapplication.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.regiscorp.myapplication.R
import com.regiscorp.myapplication.adapter.ItemAdapter
import com.regiscorp.myapplication.constants.TARGET_GITHUB_OWNER
import com.regiscorp.myapplication.constants.TARGET_GITHUB_REPO
import com.regiscorp.myapplication.databinding.MainFragmentBinding
import com.regiscorp.myapplication.model.ResourceState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ItemAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.commitsLiveData.observe(viewLifecycleOwner, { resource ->
            when (resource.state) {
                ResourceState.LOADING -> {
                    binding.message.visibility = View.VISIBLE
                    binding.message.text = getString(R.string.loading)
                    binding.recyclerView.visibility = View.GONE
                }
                ResourceState.SUCCESS -> {
                    binding.message.visibility = View.GONE
                    binding.message.text = ""
                    resource.data?.let {
                        adapter.updateList(it)
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                ResourceState.ERROR -> {
                    binding.message.visibility = View.VISIBLE
                    binding.message.text = getString(R.string.error_message, resource.message)
                    binding.recyclerView.visibility = View.GONE
                }
            }
        })
        viewModel.getCommits(owner = TARGET_GITHUB_OWNER, repo = TARGET_GITHUB_REPO)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}