package id.ngoding.nonton.home.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.ngoding.nonton.core.data.Resource
import id.ngoding.nonton.core.data.Type
import id.ngoding.nonton.core.domain.model.Movie
import id.ngoding.nonton.core.ui.movie.MovieAdapter
import id.ngoding.nonton.core.util.gone
import id.ngoding.nonton.core.util.showSnackbar
import id.ngoding.nonton.core.util.visible
import id.ngoding.nonton.databinding.FragmentMovieBinding
import id.ngoding.nonton.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModel<MovieViewModel>()
    private val adapter by lazy { MovieAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerView()
        observingMovieData()
    }

    private fun observingMovieData() {
        viewModel.getMovies().observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        setMovieData(movies.data)
                    }
                    is Resource.Error -> {
                        hideLoading()
                        showError(movies.message)
                    }
                }
            }
        }
    }

    private fun setMovieData(data: List<Movie>?) {
        adapter.setListMovie(data)
    }

    private fun showError(errorMessage: String?) {
        if (errorMessage != null) {
            binding?.root?.showSnackbar(errorMessage)
        }
    }

    private fun setRecyclerView() {
        binding?.rvMovies?.layoutManager = LinearLayoutManager(activity)
        binding?.rvMovies?.setHasFixedSize(true)
        binding?.rvMovies?.adapter = adapter
        adapter.clickMovieListener = { movie ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, movie.id)
            intent.putExtra(DetailActivity.EXTRA_TYPE, Type.TYPE_MOVIE)
            startActivity(intent)
        }
    }

    private fun hideLoading() {
        binding?.shimmerLayout?.gone()
        binding?.rvMovies?.visible()
    }

    private fun showLoading() {
        binding?.shimmerLayout?.visible()
        binding?.rvMovies?.gone()
    }

    override fun onDestroyView() {
        binding?.rvMovies?.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
