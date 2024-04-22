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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.BackendError
import ru.tinkoff.fintech.meowle.domain.MeowleResult
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.shared.add.AddCatViewModel

@AndroidEntryPoint
class AddCatFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_add, container, false)

    private val addCatViewModel: AddCatViewModel by viewModels()

    private lateinit var til_name : TextInputLayout
    private lateinit var iw_add_avatar : ShapeableImageView
    private lateinit var pickMedia : ActivityResultLauncher<PickVisualMediaRequest>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerPhotoPicker()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        val til_gender = view.findViewById<TextInputLayout>(R.id.til_gender)
        til_name = view.findViewById(R.id.til_name)
        val til_desc = view.findViewById<TextInputLayout>(R.id.til_desc)
        val et_desc = view.findViewById<TextInputEditText>(R.id.cat_description)

        val til_gender_atw = (til_gender.editText as? MaterialAutoCompleteTextView)

        val btn_add = view.findViewById<Button>(R.id.btn_add)
        iw_add_avatar = view.findViewById(R.id.iw_add_cat_avatar)
        til_gender_atw?.setSimpleItems(
            arrayOf<String>().plus(Gender.entries.map {
                getStringByGender(it)
            })
        )

        til_gender_atw?.setOnItemClickListener { adapterView, view, i, l ->
            val text = (adapterView[i] as MaterialTextView).text.toString()
            addCatViewModel.onGenderChange(getGenderByString(text))
        }

        til_name.editText?.addTextChangedListener(InputFieldTextWatcher())

        btn_add.setOnClickListener {
            hideKeyboard()
            til_name.clearFocus()
            til_gender.clearFocus()
            til_desc.clearFocus()
            addCatViewModel.onDescriptionChange(et_desc.text.toString())
            addCatViewModel.onAddCat()
        }

        iw_add_avatar.setOnClickListener {
            openPhotoPicker()
        }
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            addCatViewModel.result.collectLatest {
                val message = when (it) {
                    is MeowleResult.Error -> {
                        when (it.error) {
                            is BackendError.Business.Unknown -> it.error.message
                        }
                    }
                    is MeowleResult.Success -> requireContext().getString(R.string.add_snackbar_success_message)
                }
                val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
                toast.show()
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addCatViewModel.addCatInputState.collect {
                    if (it.nameError) {
                        til_name.error = "Поле не должно быть пустым, содержать спецсимволы, латиницу"
                    }
                    else {
                        til_name.error = null
                    }
                }
            }
        }
    }

    fun registerPhotoPicker() {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                addCatViewModel.onPhotoSelected(uri)
                Glide.with(requireContext())
                    .load(uri)
                    .sizeMultiplier(0.5f)
                    .apply(
                        RequestOptions()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    )
                    .into(iw_add_avatar)

            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }

    fun openPhotoPicker() {
        val mimeType = "image/jpeg"
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
    }
    fun hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    inner class InputFieldTextWatcher() : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            til_name.error = null
        }
        override fun afterTextChanged(s: Editable?) {
            addCatViewModel.onNameChange(s.toString())
        }
    }

    private fun getStringByGender(gender: Gender) : String =
        when (gender) {
            Gender.MALE -> resources.getString(R.string.gender_male)
            Gender.FEMALE -> resources.getString(R.string.gender_female)
            Gender.UNISEX -> resources.getString(R.string.gender_unisex)
        }

    private fun getGenderByString(genderStr: String) : Gender =
        when (genderStr) {
            resources.getString(R.string.gender_male) -> Gender.MALE
            resources.getString(R.string.gender_female) ->  Gender.FEMALE
            else -> Gender.UNISEX
        }
}
