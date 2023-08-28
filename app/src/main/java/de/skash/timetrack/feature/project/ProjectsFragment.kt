package de.skash.timetrack.feature.project

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.skash.timetrack.R
import de.skash.timetrack.core.helper.state.handle
import de.skash.timetrack.core.model.Project
import de.skash.timetrack.databinding.FragmentProjectsBinding
import de.skash.timetrack.feature.adapter.history.SearchHistoryListAdapter
import de.skash.timetrack.feature.adapter.project.ProjectsListAdapter
import java.util.UUID

@AndroidEntryPoint
class ProjectsFragment : Fragment(R.layout.fragment_projects) {

    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProjectsViewModel by viewModels()

    private val projectListAdapter = ProjectsListAdapter(onProjectClicked = {

    })

    private val searchHistoryAdapter = SearchHistoryListAdapter(onEntryClicked = {
        binding.searchView.setText(it.title)
        syncSearchBarWithSearchView()
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectsBinding.bind(view)
        binding.searchView.setupWithSearchBar(binding.searchBar)

        binding.projectsRecyclerView.adapter = projectListAdapter
        binding.historyRecyclerView.adapter = searchHistoryAdapter

        viewModel.searchHistoryLiveData.observe(viewLifecycleOwner) {
            it.handle(requireContext(), null, onSuccess = { entries ->
                searchHistoryAdapter.submitList(entries)
            })
        }

        projectListAdapter.submitList(
            listOf(
                Project(UUID.randomUUID(), "Test Title", "#AABBCC"),
                Project(UUID.randomUUID(), "Test Title 2", "#AABBDD")
            )
        )

        binding.searchView.editText.setOnEditorActionListener { _, _, _ ->
            syncSearchBarWithSearchView()
            return@setOnEditorActionListener false
        }

        binding.searchView.editText.addTextChangedListener {
            viewModel.fetchSearchHistory(it.toString())
        }
    }

    private fun syncSearchBarWithSearchView() {
        val query = binding.searchView.text.toString()
        binding.searchBar.text = query
        binding.searchView.hide()
        viewModel.cacheSearchHistoryEntry(query)
        viewModel.fetchProjects(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}