package com.arun.nycschools.view.schooldetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arun.nycschools.R
import com.arun.nycschools.databinding.FragmentSchoolDetailBinding
import com.arun.nycschools.viewmodel.SatMarksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 *  Fragment [SchoolDetailFragment]  class responsible for Handling SatMarks Details Page
 * */
@AndroidEntryPoint
class SchoolDetailFragment : Fragment() {

    companion object {
        const val DBN = "dbn"
        const val SCHOOL_NAME = "school_name"
        private const val TAG = "SchoolDetailFragment"
    }

    private val viewModel: SatMarksViewModel by viewModels()
    private var _binding: FragmentSchoolDetailBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbn = arguments?.getString(DBN) ?: ""
        Log.d(TAG, "Test - DBN - $dbn")
        viewModel.getAverageSatMarks(dbn)
        startDetail()
    }

    /**
     * [startDetail] method is responsible for initiating the flow collection from
     * [SatMarksViewModel]
     * */
    private fun startDetail() {
        handleError()
        initiateLoading()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    (requireActivity() as AppCompatActivity).supportActionBar?.title = arguments?.getString(SCHOOL_NAME) ?: ""
                    viewModel.schoolMarks.collect { data ->
                        with(binding) {
                            tvSchool.text = data.school_name
                            tvTestTakers.text = requireContext().getString(R.string.avg_test_takers, data.num_of_sat_test_takers)
                            tvMathScore.text = requireContext().getString(R.string.avg_math_score, data.sat_math_avg_score)
                            tvWritingScore.text = requireContext().getString(R.string.avg_writing_score, data.sat_writing_avg_score)
                            tvReadingScore.text = requireContext().getString(R.string.avg_reading_score, data.sat_critical_reading_avg_score)
                        }
                    }
                }
            }
        }
    }

    /**
     * [handleError] method collects uiState Flow and handles displaying error layout
     * */
    private fun handleError() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                binding.errorLayout.visibility = if(it.isError) View.VISIBLE else View.GONE
            }
        }
    }

    /**
     * [initiateLoading] method collects uiState Flow and handles loading layout
     * */
    private fun initiateLoading() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                binding.progressBar.visibility = if(it.isLoading) View.VISIBLE else View.GONE
            }
        }
    }

}