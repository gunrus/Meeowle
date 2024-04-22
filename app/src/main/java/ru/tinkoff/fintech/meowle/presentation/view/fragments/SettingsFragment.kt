package ru.tinkoff.fintech.meowle.presentation.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
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
                Log.i("Auth", "Logout success")
                launchAuthActivity(requireContext())
            }
        }
        return binding.root
    }
}
