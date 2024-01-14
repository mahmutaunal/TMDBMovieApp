package com.mahmutalperenunal.tmdbmovieapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmutalperenunal.tmdbmovieapp.R
import com.mahmutalperenunal.tmdbmovieapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initializeSpinner()
        initializeSearchView()
        observeEvents()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Set binding to null (memory leak prevention)
        _binding = null
    }

    // Function that observes and updates changes in the view
    @SuppressLint("SetTextI18n")
    private fun observeEvents() {
        // Observer monitoring the error message
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            binding.textViewHomeError.text = error
            binding.textViewHomeError.isVisible = true
        }

        // Observer monitoring the visibility of ProgressDialog
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
            binding.homeRecyclerView.isVisible = !loading
        }

        // Observer watching the movie list
        viewModel.movieList.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                binding.textViewHomeError.text = "There is any movie :("
                binding.textViewHomeError.isVisible = true
            } else {
                movieAdapter = MovieAdapter(list, object : MovieClickListener {
                    override fun onMovieClicked(movieId: Int?) {
                        movieId?.let {
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                            findNavController().navigate(action)
                        }
                    }
                })
                binding.homeRecyclerView.adapter = movieAdapter
            }
        }

        // Observer watching filtered movie list
        viewModel.filteredMovieList.observe(viewLifecycleOwner) { filteredList ->
            if (filteredList.isNullOrEmpty()) {
                binding.textViewHomeError.text = "No matching movies found :("
                binding.textViewHomeError.isVisible = true
                binding.homeRecyclerView.isVisible = false
            } else {
                binding.textViewHomeError.isVisible = false
                binding.homeRecyclerView.isVisible = true

                movieAdapter = MovieAdapter(filteredList, object : MovieClickListener {
                    override fun onMovieClicked(movieId: Int?) {
                        binding.searchView.setQuery("", false)
                        binding.searchView.isIconified = true

                        movieId?.let {
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                            findNavController().navigate(action)
                        }
                    }
                })
                binding.homeRecyclerView.adapter = movieAdapter
            }
        }
    }

    private fun initializeSpinner() {
        val spinner: Spinner = binding.spinner

        val spinnerItems = resources.getStringArray(R.array.filter_array)

        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filter_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val spinnerPosition = spinnerAdapter.getPosition("Popular")
        spinner.setSelection(spinnerPosition)

        // Listener called when Spinner elements change
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = spinnerItems[position]
                viewModel.onCategorySelected(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun initializeSearchView() {
        val searchView: SearchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filterMovies(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterMovies(newText.orEmpty())
                return true
            }
        })

        searchView.queryHint = "Search movies"
    }
}