package com.example.desafioframework.presentation.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.desafioframework.databinding.FragmentPostBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val viewModel by viewModel<PostViewModel>()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}