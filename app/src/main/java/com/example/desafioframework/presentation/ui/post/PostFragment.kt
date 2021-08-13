package com.example.desafioframework.presentation.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafioframework.R
import com.example.desafioframework.core.AppState
import com.example.desafioframework.core.createDialog
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.databinding.FragmentPostBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val viewModel by viewModel<PostViewModel>()
    private val postListAdapter by lazy { PostListAdapter() }

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupList()
        setupListeners()
        initViewModel()
        initObservers()
    }

    private fun setupList() {
        binding.rvPostList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = postListAdapter
        }
    }

    private fun setupListeners() {
        binding.btRefreshPosts.setOnClickListener {
            refreshPosts()
        }
    }

    private fun initViewModel() {
        showLoading(true)
        viewModel.init()
    }

    private fun initObservers() {
        viewModel.postLiveData.observe(this) { state ->
            when (state) {
                is AppState.Error -> {
                    context?.createDialog {
                        setMessage(setupErrorMessage(state.message))
                        setCancelable(false)
                        setPositiveButton(R.string.common_reload) { _, _ -> refreshPosts() }
                    }?.show()
                    showLoading(false)
                }
                is AppState.Success<*> -> {
                    val response = state.successData as List<Post>
                    postListAdapter.submitList(response)
                    setupLayout(response)
                    showLoading(false)
                }
            }
        }
    }

    private fun setupLayout(list: List<Post>) {
        if (!list.isNullOrEmpty()) {
            binding.rvPostList.visibility = View.VISIBLE
            binding.tvPostsNotFound.visibility = View.GONE
            binding.btRefreshPosts.visibility = View.GONE
        } else {
            binding.rvPostList.visibility = View.GONE
            binding.tvPostsNotFound.visibility = View.VISIBLE
            binding.btRefreshPosts.visibility = View.VISIBLE
        }
    }

    private fun refreshPosts() {
        binding.tvPostsNotFound.visibility = View.GONE
        binding.btRefreshPosts.visibility = View.GONE

        initViewModel()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.cpiLoadingPosts.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupErrorMessage(apiMessage: String): String {
        return if (apiMessage.isNullOrEmpty()) getString(R.string.generic_error_message) else apiMessage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}