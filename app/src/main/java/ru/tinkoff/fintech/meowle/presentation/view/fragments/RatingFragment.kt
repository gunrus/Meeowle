package ru.tinkoff.fintech.meowle.presentation.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Vote
import ru.tinkoff.fintech.meowle.presentation.shared.rating.RatingViewModel
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.rating.CatListAdapter

@AndroidEntryPoint
class RatingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View = inflater.inflate(R.layout.rating_fragment, container, false)

    private val ratingViewModel: RatingViewModel by lazy {
        ViewModelProvider(this)[RatingViewModel::class.java]
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentItemClickListener = context as FragmentItemClickListener
    }

    lateinit var fragmentItemClickListener : FragmentItemClickListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        uploadRating(Vote.LIKES)
        setUpRecycler(view)
        initTabLayout()
        setupClickListener()
    }

    private lateinit var adapter : CatListAdapter
    private lateinit var tabLayout : TabLayout

    private fun setUpRecycler(view : View) {
        val rvPersonList = view.findViewById<RecyclerView>(R.id.rv_cats_list)
        tabLayout = view.findViewById(R.id.tab_layout)
        rvPersonList.layoutManager = LinearLayoutManager(context)

        adapter = CatListAdapter()

        rvPersonList.adapter = adapter
    }

    private fun initViewModel() {
        lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    ratingViewModel.catsRating.collect {
                        adapter.submitList(it.cats)
                    }
            }
        }
    }

    private fun setupClickListener() {
        adapter.onCatItemClickListener = {
            Log.d("MainActivity", it.toString())
            fragmentItemClickListener.onButtonClicked(it)
        }
    }

    private fun uploadRating(vote: Vote){
        ratingViewModel.loadRating(vote)
    }

    private fun initTabLayout(){
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {

                val vote = when (tab.position) {
                    0 ->  {
                        adapter.votePosition = Vote.LIKES
                        Vote.LIKES
                    }
                    else -> {
                        adapter.votePosition = Vote.DISLIKES
                        Vote.DISLIKES
                    }
                }
                uploadRating(vote)
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }
}
