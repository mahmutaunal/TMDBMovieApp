package com.mahmutalperenunal.tmdbmovieapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mahmutalperenunal.tmdbmovieapp.MainActivity
import com.mahmutalperenunal.tmdbmovieapp.databinding.FragmentDetailBinding
import com.mahmutalperenunal.tmdbmovieapp.util.loadImage

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel.getMovieDetail(movieId = args.movieId)
        observeEvents()

        return binding.root
    }

    // Function that observes changes from ViewModel
    private fun observeEvents() {
        // Observer monitoring the visibility of ProgressDialog
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBarDetail.isVisible = it
        }

        // Observer that monitors the content and visibility of the TextView that displays the error message
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.textViewErrorDetail.text = it
            binding.textViewErrorDetail.isVisible = true
        }

        // Observer that updates UI elements showing movie details
        viewModel.movieResponse.observe(viewLifecycleOwner) { movie ->
            binding.imageViewDetail.loadImage(movie.backdropPath)

            binding.textViewDetailVote.text = movie.voteAverage.toString()
            binding.textViewDetailStudio.text = movie.productionCompanies?.first()?.name
            binding.textViewDetailLanguage.text = movie.spokenLanguages?.first()?.englishName

            binding.textViewDetailOverview.text = movie.overview

            // Make UI elements visible to show Movie details
            binding.movieDetailGroup.isVisible = true

            // Update ActionBar header (by accessing MainActivity)
            (requireActivity() as MainActivity).supportActionBar?.title = movie.title
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Set binding to null (memory leak prevention)
        _binding = null
    }
}