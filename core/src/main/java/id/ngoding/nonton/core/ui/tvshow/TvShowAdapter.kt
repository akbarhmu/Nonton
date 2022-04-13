package id.ngoding.nonton.core.ui.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ngoding.nonton.core.data.Image
import id.ngoding.nonton.core.databinding.ItemMovieBinding
import id.ngoding.nonton.core.domain.model.TvShow
import id.ngoding.nonton.core.util.loadImageUrl

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private var listTvShow = ArrayList<TvShow>()
    var clickTvShowListener: ((TvShow) -> Unit)? = {}

    fun setListTvShow(listTvShow: List<TvShow>?) {
        if (listTvShow == null) return
        val diffCallback = TvShowDiffCallback(this.listTvShow, listTvShow)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listTvShow.clear()
        this.listTvShow.addAll(listTvShow)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemMovieBinding)
    }

    override fun getItemCount(): Int = listTvShow.size

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(listTvShow[position])
    }

    inner class TvShowViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TvShow) {
            binding.tvTitle.text = tvShow.name
            binding.tvDescription.text = tvShow.overview
            binding.rbRating.rating = tvShow.voteAverage?.div(2)!!.toFloat()
            binding.ivPoster.loadImageUrl(Image.IMAGE_URL + Image.SIZE + tvShow.posterPath)
        }

        init {
            binding.root.setOnClickListener {
                clickTvShowListener?.invoke(listTvShow[adapterPosition])
            }
        }
    }

}