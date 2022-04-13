package id.ngoding.nonton.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.ngoding.nonton.core.data.Type
import id.ngoding.nonton.core.ui.tvshow.TvShowAdapter
import id.ngoding.nonton.core.util.gone
import id.ngoding.nonton.core.util.visible
import id.ngoding.nonton.detail.DetailActivity
import id.ngoding.nonton.favorite.databinding.FragmentFavoriteTvShowBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTvShowFragment : Fragment() {

    private var _binding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModel<FavoriteTvShowViewModel>()
    private val adapter by lazy { TvShowAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoriteTvShowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        startObservingTvShowData()
    }

    private fun setUpRecyclerView() {
        binding?.rvFavoriteTvShows?.layoutManager = LinearLayoutManager(activity)
        binding?.rvFavoriteTvShows?.setHasFixedSize(true)
        binding?.rvFavoriteTvShows?.adapter = adapter
        adapter.clickTvShowListener = { tvShow ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, tvShow.id)
            intent.putExtra(DetailActivity.EXTRA_TYPE, Type.TYPE_TVSHOW)
            startActivity(intent)
        }
    }

    private fun startObservingTvShowData() {
        viewModel.getFavoriteTvShow().observe(viewLifecycleOwner) { tvShow ->
            if (tvShow.isNullOrEmpty()) {
                binding?.shimmerLayout?.gone()
                binding?.rvFavoriteTvShows?.gone()
            } else {
                binding?.shimmerLayout?.gone()
                binding?.rvFavoriteTvShows?.visible()
                adapter.setListTvShow(tvShow)
            }
        }
    }

    override fun onDestroyView() {
        binding?.rvFavoriteTvShows?.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
