package ru.tinkoff.fintech.meowle.presentation.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.shared.details.CatDetailsViewModel
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.photos.PhotoListAdapter


@AndroidEntryPoint
class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.details_fragment, container, false)

    private val detailsViewModel: CatDetailsViewModel by viewModels()

    lateinit var tw_Title : TextView
    lateinit var iw_Avatar : ImageView
    lateinit var tw_Name : TextView
    lateinit var iw_Gender : ImageView
    lateinit var tw_Likes : TextView
    lateinit var tw_Dislikes : TextView
    lateinit var tw_Desc : TextView
    lateinit var btn_like : ImageButton
    lateinit var btn_edit : Button
    lateinit var btn_dislike : ImageButton
    lateinit var ib_favorite : ImageButton
    lateinit var rv_photos  : RecyclerView
    private var currentVote : Int = 0
    private lateinit var adapter : PhotoListAdapter

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.cat.collect {
                    tw_Title.text = "Досье по ${it.name}"
                    tw_Name.text = it.name
                    tw_Likes.text = it.likes.toString()
                    tw_Dislikes.text = it.dislikes.toString()
                    tw_Desc.text = it.description

                    when (it.gender) {
                        Gender.MALE -> iw_Gender.setImageDrawable(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_male,null ))
                        Gender.FEMALE -> iw_Gender.setImageDrawable(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_female,null ))
                        Gender.UNISEX -> iw_Gender.setImageDrawable(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_unknown_gender,null ))
                    }

                    tw_Likes.text = "\uD83D\uDC4D ${it.likes}"
                    tw_Dislikes.text = "\uD83D\uDC4E ${it.dislikes}"
                }
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.catDetailsState.collect {
                    when (it.isFavorite) {
                        true -> ib_favorite.setImageDrawable(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_favourite_pressed,null ))
                        false -> ib_favorite.setImageDrawable(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_favourite,null ))
                    }

                    if (it.catPhotoUrls.isNotEmpty()) {
                        Glide.with(requireContext())
                            .load(it.catPhotoUrls.first())
                            .into(iw_Avatar)
                        adapter.submitList(it.catPhotoUrls)

                    }
                }
            }
        }
    }

    private fun loadCat(cat: Cat) {
        detailsViewModel.updateCat(cat)
        detailsViewModel.loadCatDetails(cat)
    }

    private fun viewVote(state: Boolean) {
        if (state){
            //Like
            btn_like.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_like_pressed, null ))
            btn_dislike.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_dislike, null ))
            detailsViewModel.onVoteClicked(true)
        } else {
            //DisLike
            btn_dislike.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_dislike_pressed, null ))
            btn_like.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_like, null ))
            detailsViewModel.onVoteClicked(false)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            initViewModel()
            tw_Title = view.findViewById(R.id.tw_details_title)
            iw_Avatar = view.findViewById(R.id.cat_avatar)
            tw_Name = view.findViewById(R.id.cat_name)
            iw_Gender = view.findViewById(R.id.cat_gender)
            tw_Likes = view.findViewById(R.id.cat_likes)
            tw_Dislikes = view.findViewById(R.id.cat_dislikes)
            tw_Desc = view.findViewById(R.id.cat_description)
            rv_photos = view.findViewById(R.id.rv_photos)
            btn_like = view.findViewById(R.id.ib_like)
            btn_dislike = view.findViewById(R.id.ib_dislike)
            btn_edit = view.findViewById(R.id.btn_edit)
            ib_favorite = view.findViewById(R.id.ib_favorite)

            rv_photos.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            adapter = PhotoListAdapter()
            rv_photos.adapter = adapter

            btn_like.setOnClickListener{
               if (currentVote <=0) {
                   viewVote(true)
                   currentVote = 1
               }
            }

            ib_favorite.setOnClickListener {
                detailsViewModel.onFavoriteClicked()
            }
            btn_dislike.setOnClickListener{
                if (currentVote >=0) {
                    viewVote(false)
                    currentVote = -1
                }
            }
            btn_edit.setOnClickListener {

                if (tw_Desc.isEnabled) {
                    tw_Desc.isEnabled = false
                    btn_edit.text = resources.getString(R.string.btn_edit)
                    detailsViewModel.onCatDescriptionChange(tw_Desc.text.toString())
                    detailsViewModel.onSaveDescriptionClicked()
                }
                else {
                    tw_Desc.isEnabled = true
                    btn_edit.text = resources.getString(R.string.btn_save)
                }

            }

            val cat: Cat = requireArguments().getParcelable("cat")!!
            loadCat(cat)
        }
    }
}


