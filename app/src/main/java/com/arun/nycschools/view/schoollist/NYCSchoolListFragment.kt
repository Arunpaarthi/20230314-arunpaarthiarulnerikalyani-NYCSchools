package com.arun.nycschools.view.schoollist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arun.nycschools.R
import com.arun.nycschools.databinding.FragmentSchoolListBinding
import com.arun.nycschools.viewmodel.SchoolListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NYCSchoolListFragment : Fragment() {

    companion object {
        private const val TAG = "NYCSchoolListFragment"
    }

    private val viewModel: SchoolListViewModel by viewModels()

    private var _binding: FragmentSchoolListBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var adapter: NYCSchoolListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolListBinding.inflate(inflater, container, false)
        startList()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NYCSchoolListAdapter { school ->
            view.findNavController().navigate(
                NYCSchoolListFragmentDirections.actionNYCSchoolListFragmentToSchoolDetailFragment(
                    dbn = school.dbn,
                    schoolName = school.school_name
                )
            )
        }
        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            val dividerItemDecoration = DividerItemDecoration(
                recyclerView.context,
                layoutManager.orientation
            )
            recyclerView.addItemDecoration(dividerItemDecoration)
            binding.recyclerView.adapter = adapter
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.nyc_school_title)
        viewModel.getSchools()
    }

    private fun startList() {
        handleError()
        initiateLoading()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.schools.collect { schools ->
                        if (schools.isNotEmpty()) {
                            adapter.submitList(schools)
                        }
                    }
                }
            }
        }
    }

    private fun handleError() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                binding.errorLayout.visibility = if(it.isError) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initiateLoading() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                binding.progressBar.visibility = if(it.isLoading) View.VISIBLE else View.GONE
            }
        }
    }

}