package id.ngoding.nonton.home.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.ngoding.nonton.core.data.Resource
import id.ngoding.nonton.core.data.Type
import id.ngoding.nonton.core.domain.model.TvShow
import id.ngoding.nonton.core.ui.tvshow.TvShowAdapter
import id.ngoding.nonton.core.util.gone
import id.ngoding.nonton.core.util.showSnackbar
import id.ngoding.nonton.core.util.visible
import id.ngoding.nonton.databinding.FragmentTvShowBinding
import id.ngoding.nonton.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModel<TvShowViewModel>()
    private val adapter by lazy { TvShowAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerView()
        observingTvShowData()
    }

    private fun observingTvShowData() {
        viewModel.getTvShow().observe(viewLifecycleOwner) { tvShows ->
            if (tvShows != null) {
                when (tvShows) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        setTvShowData(tvShows.data)
                    }
                    is Resource.Error -> {
                        hideLoading()
                        showError(tvShows.message)
                    }
                }
            }
        }
    }

    private fun setTvShowData(data: List<TvShow>?) {
        adapter.setListTvShow(data)
    }

    private fun showError(errorMessage: String?) {
        if (errorMessage != null) {
            binding?.root?.showSnackbar(errorMessage)
        }
    }

    private fun setRecyclerView() {
        binding?.rvTvShow?.layoutManager = LinearLayoutManager(activity)
        binding?.rvTvShow?.setHasFixedSize(true)
        binding?.rvTvShow?.adapter = adapter
        adapter.clickTvShowListener = { tvShow ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, tvShow.id)
            intent.putExtra(DetailActivity.EXTRA_TYPE, Type.TYPE_TVSHOW)
            startActivity(intent)
        }
    }

    private fun hideLoading() {
        binding?.shimmerLayout?.gone()
        binding?.rvTvShow?.visible()
    }

    private fun showLoading() {
        binding?.shimmerLayout?.visible()
        binding?.rvTvShow?.gone()
    }

    override fun onDestroyView() {
        binding?.rvTvShow?.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
