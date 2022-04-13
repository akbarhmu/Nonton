package id.ngoding.nonton.core.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ngoding.nonton.core.data.Image
import id.ngoding.nonton.core.databinding.ItemMovieBinding
import id.ngoding.nonton.core.domain.model.Movie
import id.ngoding.nonton.core.util.loadImageUrl

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var listMovie = ArrayList<Movie>()
    var clickMovieListener: ((Movie) -> Unit)? = {}

    fun setListMovie(listMovie: List<Movie>?) {
        if (listMovie == null) return
        val diffCallback = MovieDiffCallback(this.listMovie, listMovie)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title
            binding.tvDescription.text = movie.overview
            binding.rbRating.rating = movie.voteAverage?.div(2)!!.toFloat()
            binding.ivPoster.loadImageUrl(Image.IMAGE_URL + Image.SIZE + movie.posterPath)
        }

        init {
            binding.root.setOnClickListener {
                clickMovieListener?.invoke(listMovie[adapterPosition])
            }
        }
    }


}