package ru.tinkoff.fintech.meowle.presentation.view.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.shared.favourites.FavouritesViewModel
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.favorites.FavoritesCatsAdapter

@AndroidEntryPoint
class FavouriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            val exception = IllegalStateException("You spin me right round, baby, right round")
            Log.e("Crash", exception.message.toString(), exception)
            throw exception
        } else {
            // In portrait
            return inflater.inflate(R.layout.favorite_fragment, container, false)
        }
    }

    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private lateinit var fragmentItemClickListener : FragmentItemClickListener
    private lateinit var adapter : FavoritesCatsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentItemClickListener = context as FragmentItemClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val title = view.findViewById<TextView>(R.id.tw_rating_title)
//        title.text = resources.getString(R.string.favourites_title)

        uploadFavourites()
        initViewModel()
        setUpRecycler(view)
        setupClickListener()
    }

    private fun uploadFavourites() {
        favouritesViewModel.loadFavouriteCats()
    }

    private fun setupClickListener() {
        adapter.onCatItemClicked = {
            fragmentItemClickListener.onButtonClicked(it)
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favouritesViewModel.favouriteCats.collect { favouriteCats ->
                    adapter.submitList(favouriteCats)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favouritesViewModel.isCatsLoaded.collect {
                    favouritesViewModel.loadPhotos()
                }
            }
        }
    }

    private fun setUpRecycler(view : View) {

        val rvPersonList = view.findViewById<RecyclerView>(R.id.rv_cats_list)
        rvPersonList.layoutManager = GridLayoutManager(context,2)

        adapter = FavoritesCatsAdapter()
        rvPersonList.adapter = adapter
    }
}