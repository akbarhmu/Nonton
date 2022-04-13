package id.ngoding.nonton.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.ngoding.nonton.favorite.movie.FavoriteMovieFragment
import id.ngoding.nonton.favorite.tvshow.FavoriteTvShowFragment

class FavoriteViewPagerAdapter(activity: FavoriteFragment) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMovieFragment()
            1 -> FavoriteTvShowFragment()
            else -> Fragment()
        }
    }

    companion object {
        private const val NUM_TABS = 2
    }
}