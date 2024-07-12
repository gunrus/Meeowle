package ru.tinkoff.fintech.meowle.presentation.view.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.databinding.SettingsFragmentBinding
import ru.tinkoff.fintech.meowle.presentation.shared.settings.SettingsViewModel
import ru.tinkoff.fintech.meowle.presentation.shared.settings.launchAuthActivity
import ru.tinkoff.fintech.meowle.presentation.shared.settings.launchMainActivity

@AndroidEntryPoint
class SettingsFragment  : Fragment() {

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var dialog : BottomSheetDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: SettingsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        binding.viewModel = viewModel

        viewModel.loadSettings()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.applySettings.collectLatest {
                if (viewModel.state.value.isCompose) {
                    launchMainActivity(requireContext())
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.logout.collectLatest {
                launchAuthActivity(requireContext())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                binding.twDescName.text = it.name
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bottomSheetState.collectLatest {
                if (it.isShown)
                    if (!dialog.isShowing)
                        showBottomSheet()
                else
                    dialog.dismiss()
            }
        }
        dialog = BottomSheetDialog(requireContext())

        return binding.root
    }

    private fun showBottomSheet() {
        dialog = BottomSheetDialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val bottomSheet = layoutInflater.inflate(R.layout.change_name_bottomsheet_dialog, null)

        val til_name = bottomSheet.findViewById<TextInputLayout>(R.id.til_name)

        til_name.editText?.setText(viewModel.state.value.name)

        bottomSheet.findViewById<Button>(R.id.confirm_button).setOnClickListener {
            viewModel.onUserNameChanged(til_name.editText?.text.toString())
            viewModel.onSaveUserName()
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            viewModel.onCloseBottomSheet()
        }

        with(dialog) {
            setContentView(bottomSheet)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.attributes?.windowAnimations = R.style.DialogSheetAnimation
            show()
        }
    }
}
