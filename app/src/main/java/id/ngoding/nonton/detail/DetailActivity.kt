package id.ngoding.nonton.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ShareCompat
import id.ngoding.nonton.R
import id.ngoding.nonton.core.data.Image
import id.ngoding.nonton.core.data.Type.TYPE_MOVIE
import id.ngoding.nonton.core.data.Type.TYPE_TVSHOW
import id.ngoding.nonton.core.domain.model.Movie
import id.ngoding.nonton.core.domain.model.TvShow
import id.ngoding.nonton.core.util.gone
import id.ngoding.nonton.core.util.loadImageUrl
import id.ngoding.nonton.core.util.visible
import id.ngoding.nonton.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModel<DetailViewModel>()
    private var isFavorite = false
    private var type: String? = null
    private var movieCopy: Movie? = null
    private var tvShowCopy: TvShow? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_DATA, 0)
        type = intent.getStringExtra(EXTRA_TYPE)

        getDetail(type, id)

        binding.apply {
            btnAddFavorite.setOnClickListener {
                setFavorite(movieCopy, tvShowCopy)
            }
            btnDeleteFavorite.setOnClickListener {
                setFavorite(movieCopy, tvShowCopy)
            }
            btnShare.setOnClickListener {
                ShareCompat.IntentBuilder(root.context)
                    .setType("text/plain")
                    .setChooserTitle(getString(R.string.share_title))
                    .setText(getString(R.string.share_message, if (movieCopy != null) movieCopy?.title else tvShowCopy?.name))
                    .startChooser()
            }
            btnBack.setOnClickListener { finish() }
        }
    }

    private fun getDetail(type: String?, id: Int) {
        if (type.equals(TYPE_MOVIE, ignoreCase = true)) {
            observingDetailMovie(id)
        } else if (type.equals(TYPE_TVSHOW, ignoreCase = true)) {
            observingDetailTvShow(id)
        }
    }

    private fun observingDetailMovie(id: Int) {
        viewModel.getDetailMovieById(id).observe(this) { movie ->
            setDetailMovies(movie)
        }
    }

    private fun observingDetailTvShow(id: Int) {
        viewModel.getDetailTvShowById(id).observe(this) { tvShow ->
            setDetailTvShows(tvShow)
        }
    }

    private fun setDetailTvShows(tvShow: TvShow) {
        binding.ivPoster.loadImageUrl(Image.IMAGE_URL + Image.SIZE + tvShow.backdropPath)
        binding.tvTitle.text = tvShow.name
        binding.ratingBar.numStars = RATING_STAR_COUNT
        binding.ratingBar.rating = tvShow.voteAverage?.div(2)!!.toFloat()
        binding.tvDate.text = tvShow.firstAirDate
        binding.tvDescription.text = tvShow.overview
        isFavorite = tvShow.isFavorite
        tvShowCopy = tvShow

        setFavorite()
    }

    private fun setDetailMovies(movie: Movie) {
        binding.ivPoster.loadImageUrl(Image.IMAGE_URL + Image.SIZE + movie.backdropPath)
        binding.tvTitle.text = movie.title
        binding.ratingBar.numStars = RATING_STAR_COUNT
        binding.ratingBar.rating = movie.voteAverage?.div(2)!!.toFloat()
        binding.tvDate.text = movie.releaseDate
        binding.tvDescription.text = movie.overview
        isFavorite = movie.isFavorite
        movieCopy = movie

        setFavorite()
    }

    private fun setFavorite() {
        if (isFavorite) {
            binding.btnAddFavorite.gone()
            binding.btnDeleteFavorite.visible()
        } else {
            binding.btnAddFavorite.visible()
            binding.btnDeleteFavorite.gone()
        }
    }

    private fun setFavorite(movie: Movie?, tvShow: TvShow?) {
        if (movie != null) {
            viewModel.setFavoriteMovie(movie)
        } else if (tvShow != null) {
            viewModel.setFavoriteTvShow(tvShow)
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_TYPE = "extra_type"
        const val RATING_STAR_COUNT = 5
    }
}