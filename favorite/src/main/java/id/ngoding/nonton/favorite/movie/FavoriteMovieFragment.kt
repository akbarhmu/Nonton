package id.ngoding.nonton.favorite.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.ngoding.nonton.core.data.Type.TYPE_MOVIE
import id.ngoding.nonton.core.ui.movie.MovieAdapter
import id.ngoding.nonton.core.util.gone
import id.ngoding.nonton.core.util.visible
import id.ngoding.nonton.detail.DetailActivity
import id.ngoding.nonton.favorite.databinding.FragmentFavoriteMovieBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieFragment : Fragment() {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModel<FavoriteMovieViewModel>()
    private val adapter by lazy { MovieAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        startObservingMovieData()
    }

    private fun setUpRecyclerView() {
        binding?.rvFavoriteMovies?.layoutManager = LinearLayoutManager(activity)
        binding?.rvFavoriteMovies?.setHasFixedSize(true)
        binding?.rvFavoriteMovies?.adapter = adapter
        adapter.clickMovieListener = { movie ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, movie.id)
            intent.putExtra(DetailActivity.EXTRA_TYPE, TYPE_MOVIE)
            startActivity(intent)
        }
    }

    private fun startObservingMovieData() {
        viewModel.getFavoriteMovie().observe(viewLifecycleOwner) { movie ->
            if (movie.isNullOrEmpty()) {
                binding?.shimmerLayout?.gone()
                binding?.rvFavoriteMovies?.gone()
            } else {
                binding?.shimmerLayout?.gone()
                binding?.rvFavoriteMovies?.visible()
                adapter.setListMovie(movie)
            }
        }
    }

    override fun onDestroyView() {
        binding?.rvFavoriteMovies?.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}