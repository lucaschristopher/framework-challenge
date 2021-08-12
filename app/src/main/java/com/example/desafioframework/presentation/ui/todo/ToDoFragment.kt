package com.example.desafioframework.presentation.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafioframework.core.AppState
import com.example.desafioframework.core.createDialog
import com.example.desafioframework.data.model.ToDo
import com.example.desafioframework.databinding.FragmentTodoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ToDoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val viewModel by viewModel<ToDoViewModel>()
    private val toDoListAdapter by lazy { ToDoListAdapter() }

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupList()
        viewModel.init()
        initObservers()
    }

    private fun setupList() {
        binding.rvTodoList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = toDoListAdapter
        }
    }

    private fun initObservers() {
        viewModel.todoLiveData.observe(this) { state ->
            when (state) {
                AppState.Loading -> showLoading(true)
                is AppState.Error -> {
                    context?.createDialog {
                        setMessage(state.error.message)
                        setCancelable(false)
                        setPositiveButton(android.R.string.ok) { _, _ -> viewModel.init() }
                    }?.show()
                    showLoading(false)
                }
                is AppState.Success -> {
                    val response = state.list as List<ToDo>
                    toDoListAdapter.submitList(response)
                    setupLayout(response)
                    showLoading(false)
                }
            }
        }
    }

    private fun setupLayout(list: List<ToDo>) {
        if (!list.isNullOrEmpty()) {
            binding.rvTodoList.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        // TODO: Implements progressBar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}