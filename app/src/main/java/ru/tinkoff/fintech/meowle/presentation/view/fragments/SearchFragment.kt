package ru.tinkoff.fintech.meowle.presentation.view.fragments

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.presentation.shared.menu.GenderOption
import ru.tinkoff.fintech.meowle.presentation.shared.menu.OrderOption
import ru.tinkoff.fintech.meowle.presentation.shared.search.SearchViewModel
import ru.tinkoff.fintech.meowle.presentation.shared.search.Status
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search.CatsSearchListAdapter

@AndroidEntryPoint
class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.search_fragment, container, false)


    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentItemClickListener = context as FragmentItemClickListener
    }

    private lateinit var fragmentItemClickListener : FragmentItemClickListener
    private lateinit var adapter : CatsSearchListAdapter
    private lateinit var til_search : TextInputLayout
    private lateinit var card_search : MaterialCardView
    private lateinit var iw_on_search : ShapeableImageView
    private lateinit var tw_on_search : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler(view)
        initViews(view)
        initViewModel()

    }

    private fun onEmptyResult() {
        card_search.visibility = View.VISIBLE
        iw_on_search.setImageDrawable(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.sad_cat,null ))
        tw_on_search.setText(R.string.no_cats_found)
    }

    private fun onSuccessfulSearching() {
        card_search.visibility = View.GONE
        iw_on_search.setImageDrawable(ResourcesCompat.getDrawable(requireContext().resources, R.drawable.sleepy_cat,null ))
        tw_on_search.setText(R.string.search_lets_find_description)
    }
    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.screenState.collect {
                    if (it.status == Status.EMPTY) {
                        onEmptyResult()
                    } else {
                        val consolidatedList = mutableListOf<Cat>()
                        it.cats.forEach { cat ->
                            consolidatedList.add(cat)
                        }

                        adapter = CatsSearchListAdapter (consolidatedList)
                        setupClickListener()
                        rvPersonList.adapter = adapter
                        if (consolidatedList.isNotEmpty())
                            onSuccessfulSearching()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.inputState.collect {
                    if (it.isSearchTextValid)
                        til_search.error = null
                    else
                        til_search.error = resources.getString(R.string.error_message)
                }
            }
        }
    }

    private lateinit var rvPersonList : RecyclerView

    private fun setupClickListener() {
        adapter.onCatItemClickListener = {
            Log.d("MainActivity", it.toString())
            fragmentItemClickListener.onButtonClicked(it)
        }
    }

    private fun setUpRecycler(view : View) {
        rvPersonList = view.findViewById(R.id.rv_search_result_list)
        rvPersonList.layoutManager = LinearLayoutManager(context)

    }

    private fun initViews(view: View) {
        val tw_search = view.findViewById<TextInputEditText>(R.id.et_search)

        card_search = view.findViewById(R.id.card_lets_search)
        iw_on_search = view.findViewById(R.id.iw_cat_image)
        tw_on_search = view.findViewById(R.id.tw_on_search)
        til_search = view.findViewById(R.id.til_search)

        tw_search.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchViewModel.onSearchTextChange(tw_search.text.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                til_search.error = null
            }
        })

        til_search.setEndIconOnClickListener {
            showBottomSheet()
        }

        til_search.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                searchViewModel.onSearch()
            }
            true
        }

    }


    fun hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val bottomSheet = layoutInflater.inflate(R.layout.filter_bottomsheet_dialog, null)

        val til_gender = bottomSheet.findViewById<TextInputLayout>(R.id.til_gender)
        val til_sort = bottomSheet.findViewById<TextInputLayout>(R.id.til_sorting_method)


        with (til_gender.editText as MaterialAutoCompleteTextView){
            setSimpleItems(
                arrayOf<String>().plus(GenderOption.entries.sortedDescending().map {
                    it.toTitle(context)
                })
            )

            setOnItemClickListener { adapterView, _, i, _ ->
                val text = (adapterView[i] as MaterialTextView).text.toString()
                searchViewModel.onSearchGenderChange(getGenderByString(text))
            }

            setText(searchViewModel.inputState.value.searchGender.toTitle(context), false)
            searchViewModel.onSearchGenderChange(searchViewModel.inputState.value.searchGender)
        }

        with(til_sort.editText as MaterialAutoCompleteTextView) {
            setSimpleItems(
                arrayOf<String>().plus(OrderOption.entries.sortedDescending().map {
                    it.toTitle(context)
                })
            )
            setOnItemClickListener { adapterView, _, i, _ ->
                val text = (adapterView[i] as MaterialTextView).text.toString()
                searchViewModel.onSearchOrderChange(getSearchOrderByString(text))
            }
            setText(searchViewModel.inputState.value.searchOrder.toTitle(context), false)
            searchViewModel.onSearchOrderChange(searchViewModel.inputState.value.searchOrder)
        }

        bottomSheet.findViewById<Button>(R.id.confirm_button).setOnClickListener {
            searchViewModel.onSearch()
            dialog.dismiss()
        }


        with(dialog) {
            setContentView(bottomSheet)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.attributes?.windowAnimations = R.style.DialogSheetAnimation
            show()
        }
    }


    private fun getGenderByString(genderStr: String) : GenderOption =
        when (genderStr) {
            resources.getString(R.string.drop_down_menu_gender_option_male) -> GenderOption.MALE
            resources.getString(R.string.drop_down_menu_gender_option_female) ->  GenderOption.FEMALE
            resources.getString(R.string.drop_down_menu_gender_option_unisex) ->  GenderOption.UNISEX
            else ->  GenderOption.ALL
        }

    private fun getSearchOrderByString(orderStr: String) : OrderOption =
        when (orderStr) {
            resources.getString(R.string.drop_down_menu_sort_option_desc) ->  OrderOption.DESC
            else ->  OrderOption.ASC
        }

}
