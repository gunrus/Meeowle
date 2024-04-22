package ru.tinkoff.fintech.meowle.presentation.view.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.shared.menu.GenderOption
import ru.tinkoff.fintech.meowle.presentation.shared.menu.OrderOption
import ru.tinkoff.fintech.meowle.presentation.shared.search.SearchViewModel
import ru.tinkoff.fintech.meowle.presentation.shared.search.Status
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search.CatsSearchListAdapter
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search.DataItemSearch
import ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search.SearchCatListItem

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
    private lateinit var til_gender: TextInputLayout
    private lateinit var til_sort: TextInputLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler(view)
        initViews(view)
        initViewModel()

    }

    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.screenState.collect {
                    if (it.status == Status.EMPTY) {
                        val toast = Toast.makeText(context, resources.getString(R.string.no_cats_found), Toast.LENGTH_SHORT)
                        toast.show()
                    } else {
                        val consolidatedList = mutableListOf<SearchCatListItem>()
                        it.cats.forEach { cat ->
                            //consolidatedList.add(DividerItemSearch(cat.key))

                            consolidatedList.add(DataItemSearch(cat))
                        }

                        adapter = CatsSearchListAdapter (consolidatedList)
                        setupClickListener()
                        rvPersonList.adapter = adapter
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
        val layoutManager = FlexboxLayoutManager(context)

        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        rvPersonList.layoutManager = layoutManager

    }

    private fun initViews(view: View) {
        val btn_search = view.findViewById<Button>(R.id.search_button)
        val tw_search = view.findViewById<TextInputEditText>(R.id.et_search)

        til_search = view.findViewById(R.id.til_search)
        til_gender = view.findViewById(R.id.til_gender)
        til_sort = view.findViewById(R.id.til_sort)
        initAutoCompleteTextViews()

        tw_search.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchViewModel.onSearchTextChange(tw_search.text.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                til_search.error = null
            }
        })

        btn_search.setOnClickListener {
            searchViewModel.onSearch()
            hideKeyboard()
        }
    }

    private fun initAutoCompleteTextViews(){

        val til_gender_atw = (til_gender.editText as MaterialAutoCompleteTextView)

        til_gender_atw.setSimpleItems(
            arrayOf<String>().plus(GenderOption.entries.sortedDescending().map { getStringBySearchGender(it) })
        )
        til_gender_atw.setText(til_gender_atw.adapter.getItem(0).toString(), false)
        searchViewModel.onSearchGenderChange(GenderOption.ALL)

        til_gender_atw.setOnItemClickListener { adapterView, view, i, l ->
            val text = (adapterView[i] as MaterialTextView).text.toString()
            searchViewModel.onSearchGenderChange(getGenderByString(text))
        }

        val til_sort_atw = (til_sort.editText as MaterialAutoCompleteTextView)

        til_sort_atw.setSimpleItems(
            arrayOf<String>().plus(OrderOption.entries.map {getStringBySearchOrder(it)})
        )

        til_sort_atw.setOnItemClickListener { adapterView, _, i, _ ->
            val text = (adapterView[i] as MaterialTextView).text.toString()
            searchViewModel.onSearchOrderChange(getSearchOrderByString(text))
        }
        til_sort_atw.setText(til_sort_atw.adapter.getItem(0).toString(), false)
        searchViewModel.onSearchOrderChange(OrderOption.ASC)

    }

    private fun getStringBySearchGender(gender: GenderOption) : String =
        when (gender) {
            GenderOption.MALE -> resources.getString(R.string.gender_male)
            GenderOption.FEMALE -> resources.getString(R.string.gender_female)
            GenderOption.UNISEX -> resources.getString(R.string.gender_unisex)
            else -> resources.getString(R.string.gender_all)
        }

    private fun getGenderByString(genderStr: String) : GenderOption =
        when (genderStr) {
            resources.getString(R.string.gender_male) -> GenderOption.MALE
            resources.getString(R.string.gender_female) ->  GenderOption.FEMALE
            resources.getString(R.string.gender_unisex) ->  GenderOption.UNISEX
            else ->  GenderOption.ALL
        }

    private fun getSearchOrderByString(orderStr: String) : OrderOption =
        when (orderStr) {
            resources.getString(R.string.desc) ->  OrderOption.DESC
            else ->  OrderOption.ASC
        }

    private fun getStringBySearchOrder(order: OrderOption) : String =
        when (order) {
            OrderOption.DESC -> resources.getString(R.string.desc)
            else -> resources.getString(R.string.asc)
        }

    fun hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
