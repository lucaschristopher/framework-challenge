package com.example.desafioframework.presentation.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafioframework.R
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
        setupListeners()
        initViewModel()
        initObservers()
    }

    private fun setupList() {
        binding.rvTodoList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = toDoListAdapter
        }
    }

    private fun setupListeners() {
        binding.btRefreshTodos.setOnClickListener {
            refreshTodos()
        }
    }

    private fun initViewModel() {
        showLoading(true)
        viewModel.init()
    }

    private fun initObservers() {
        viewModel.todoLiveData.observe(this) { state ->
            when (state) {
                is AppState.Error -> {
                    context?.createDialog {
                        setMessage(setupErrorMessage(state.message))
                        setCancelable(false)
                        setPositiveButton(R.string.common_reload) { _, _ -> refreshTodos() }
                    }?.show()
                    showLoading(false)
                }
                is AppState.Success<*> -> {
                    val response = state.successData as List<ToDo>
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
            binding.tvTodosNotFound.visibility = View.GONE
            binding.btRefreshTodos.visibility = View.GONE
        } else {
            binding.rvTodoList.visibility = View.GONE
            binding.tvTodosNotFound.visibility = View.VISIBLE
            binding.btRefreshTodos.visibility = View.VISIBLE
        }
    }

    private fun refreshTodos() {
        binding.tvTodosNotFound.visibility = View.GONE
        binding.btRefreshTodos.visibility = View.GONE

        initViewModel()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.cpiLoadingTodos.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupErrorMessage(apiMessage: String): String {
        return if (apiMessage.isNullOrEmpty()) getString(R.string.generic_error_message) else apiMessage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}