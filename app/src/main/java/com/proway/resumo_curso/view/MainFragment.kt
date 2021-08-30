package com.proway.resumo_curso.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.proway.resumo_curso.GithubRepositoriesAdapter
import com.proway.resumo_curso.R
import com.proway.resumo_curso.databinding.MainFragmentBinding
import com.proway.resumo_curso.model.GithubModel
import com.proway.resumo_curso.view_model.MainViewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private val adapter: GithubRepositoriesAdapter = GithubRepositoriesAdapter()
    private val LANGUAGE = "Kotlin"

    private val observePage = Observer<Int> { page ->
        viewModel.fetchRepositories(LANGUAGE, page)
    }

    private val observeRepositories = Observer<List<GithubModel>> { list ->
        adapter.refresh(list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = MainFragmentBinding.bind(view)

        viewModel.repositories.observe(viewLifecycleOwner, observeRepositories)
        viewModel.page.observe(viewLifecycleOwner, observePage)

        binding.repositoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.repositoriesRecyclerView.adapter = adapter

        viewModel.nextPage()
    }


}