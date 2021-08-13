package com.example.desafioframework.presentation.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafioframework.R
import com.example.desafioframework.core.AppState
import com.example.desafioframework.core.createDialog
import com.example.desafioframework.data.model.Album
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.databinding.FragmentAlbumBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumFragment : Fragment() {

    private var _binding: FragmentAlbumBinding? = null
    private val viewModel by viewModel<AlbumViewModel>()
    private val albumListAdapter by lazy { AlbumListAdapter() }

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
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
        binding.rvAlbumList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = albumListAdapter
        }
    }

    private fun setupListeners() {
        binding.btRefreshAlbums.setOnClickListener {
            refreshPosts()
        }
    }

    private fun initViewModel() {
        showLoading(true)
        viewModel.init()
    }

    private fun initObservers() {
        viewModel.albumLiveData.observe(this) { state ->
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
                    val response = state.successData as List<Album>
                    albumListAdapter.submitList(response)
                    setupLayout(response)
                    showLoading(false)
                }
            }
        }
    }

    private fun setupLayout(list: List<Album>) {
        if (!list.isNullOrEmpty()) {
            binding.rvAlbumList.visibility = View.VISIBLE
            binding.tvAlbumsNotFound.visibility = View.GONE
            binding.btRefreshAlbums.visibility = View.GONE
        } else {
            binding.rvAlbumList.visibility = View.GONE
            binding.tvAlbumsNotFound.visibility = View.VISIBLE
            binding.btRefreshAlbums.visibility = View.VISIBLE
        }
    }

    private fun refreshPosts() {
        binding.tvAlbumsNotFound.visibility = View.GONE
        binding.btRefreshAlbums.visibility = View.GONE

        initViewModel()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.cpiLoadingAlbums.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupErrorMessage(apiMessage: String): String {
        return if (apiMessage.isNullOrEmpty()) getString(R.string.generic_error_message) else apiMessage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}