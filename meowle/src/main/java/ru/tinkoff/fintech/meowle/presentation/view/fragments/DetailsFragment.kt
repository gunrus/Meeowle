package ru.tinkoff.fintech.meowle.presentation.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.shared.details.CatDetailsViewModel
import ru.tinkoff.fintech.meowle.presentation.shared.menu.GenderOption
import ru.tinkoff.fintech.meowle.presentation.shared.menu.OrderOption
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.photos.PhotoListAdapter


@AndroidEntryPoint
class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.details_fragment_redesign, container, false)

    private val detailsViewModel: CatDetailsViewModel by viewModels()
    private lateinit var pickMedia : ActivityResultLauncher<PickVisualMediaRequest>

    private lateinit var fragmentCloseListener : FragmentOnNavigationClose

    lateinit var iw_Avatar : ImageView
    lateinit var tw_Name : TextView
    lateinit var iw_Gender : ImageView
    lateinit var tw_Likes : TextView
    lateinit var tw_Dislikes : TextView
    lateinit var tw_Desc : TextView
    lateinit var tw_no_photo : TextView
    lateinit var btn_like : ImageButton
    lateinit var btn_edit : Button
    lateinit var btn_dislike : ImageButton
    lateinit var ib_favorite : ImageButton
    lateinit var rv_photos  : RecyclerView
    lateinit var fab_add_photo  : FloatingActionButton
    lateinit var appBar  : MaterialToolbar
    private var currentVote : Int = 0
    private lateinit var adapter : PhotoListAdapter

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.cat.collect {
                    tw_Name.text = it.name
                    tw_Likes.text = it.likes.toString()
                    tw_Dislikes.text = it.dislikes.toString()
                    tw_Desc.text = it.description
                    when (it.gender) {
                        Gender.MALE -> iw_Gender.setImageDrawable(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_male,null ))
                        Gender.FEMALE -> iw_Gender.setImageDrawable(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_female,null ))
                        Gender.UNISEX -> iw_Gender.setImageDrawable(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_unknown_gender,null ))
                    }

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
                        tw_no_photo.visibility = View.GONE
                        Glide.with(requireContext())
                            .load(it.catPhotoUrls.first())
                            .sizeMultiplier(0.7f)
                            .apply(
                                RequestOptions()
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            )
                            .into(iw_Avatar)
                        adapter.submitList(it.catPhotoUrls)

                    }
                    else
                        tw_no_photo.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCloseListener = context as FragmentOnNavigationClose
        registerPhotoPicker()

        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fragmentCloseListener.onClosePressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }



    fun registerPhotoPicker() {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                detailsViewModel.onNewCatPhotoSelected(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }

    fun openPhotoPicker() {
        val mimeType = "image/jpeg"
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
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
            iw_Avatar = view.findViewById(R.id.cat_avatar)
            tw_Name = view.findViewById(R.id.cat_name)
            iw_Gender = view.findViewById(R.id.cat_gender)
            tw_Likes = view.findViewById(R.id.tw_likes)
            tw_Dislikes = view.findViewById(R.id.tw_dislikes)
            tw_Desc = view.findViewById(R.id.cat_description)
            tw_no_photo = view.findViewById(R.id.tw_no_photos)
            rv_photos = view.findViewById(R.id.rv_photos)
            btn_like = view.findViewById(R.id.ib_like)
            btn_dislike = view.findViewById(R.id.ib_dislike)
            btn_edit = view.findViewById(R.id.btn_edit)
            ib_favorite = view.findViewById(R.id.ib_favorite)
            fab_add_photo = view.findViewById(R.id.add_photo)
            appBar = view.findViewById(R.id.topAppBar)

            rv_photos.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = PhotoListAdapter()
            rv_photos.adapter = adapter

            appBar.setNavigationOnClickListener {
                fragmentCloseListener.onClosePressed()
            }

            fab_add_photo.setOnClickListener {
                openPhotoPicker()
            }

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
                showBottomSheet()
            }

            val cat: Cat = requireArguments().getParcelable("cat")!!
            loadCat(cat)
        }
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val bottomSheet = layoutInflater.inflate(R.layout.description_bottomsheet_dialog, null)

        val til_sort = bottomSheet.findViewById<TextInputLayout>(R.id.til_desc)

        if (tw_Desc.text.isNotEmpty()) {
            til_sort.editText?.setText(tw_Desc.text.toString())
        }

        bottomSheet.findViewById<Button>(R.id.confirm_button).setOnClickListener {
            detailsViewModel.onCatDescriptionChange(til_sort.editText?.text.toString())
            detailsViewModel.onSaveDescriptionClicked()
            dialog.dismiss()
        }


        with(dialog) {
            setContentView(bottomSheet)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.attributes?.windowAnimations = R.style.DialogSheetAnimation
            show()
        }
    }
}


