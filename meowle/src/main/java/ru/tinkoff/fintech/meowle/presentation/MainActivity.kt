package ru.tinkoff.fintech.meowle.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.presentation.compose.MeowleApp
import ru.tinkoff.fintech.meowle.presentation.view.fragments.AddCatFragment
import ru.tinkoff.fintech.meowle.presentation.view.fragments.DetailsFragment
import ru.tinkoff.fintech.meowle.presentation.view.fragments.FavouriteFragment
import ru.tinkoff.fintech.meowle.presentation.view.fragments.FragmentItemClickListener
import ru.tinkoff.fintech.meowle.presentation.view.fragments.FragmentOnNavigationClose
import ru.tinkoff.fintech.meowle.presentation.view.fragments.RatingFragment
import ru.tinkoff.fintech.meowle.presentation.view.fragments.SearchFragment
import ru.tinkoff.fintech.meowle.presentation.view.fragments.SettingsFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentItemClickListener, FragmentOnNavigationClose {

    private lateinit var bottomNavView : BottomNavigationView

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadMode()
        when (viewModel.mode.value) {
            Mode.COMPOSE -> drawViaCompose()
            Mode.VIEWS -> drawViaViews()
        }
    }

    private fun drawViaCompose() {
        enableEdgeToEdge()
        setContent {
            MeowleApp()
        }
    }

    private fun drawViaViews() {
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottom_nav)
        bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab_btn_rating -> {
                    loadFragment(RatingFragment())
                    true
                }
                R.id.tab_btn_search -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.tab_btn_add -> {
                    loadFragment(AddCatFragment())
                    true
                }
                R.id.tab_btn_favourite -> {
                    loadFragment(FavouriteFragment())
                    true
                }
                R.id.tab_btn_settings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

        bottomNavView.selectedItemId = R.id.tab_btn_search
    }

    private  fun loadFragment(fragment: Fragment)  {
        bottomNavView.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout,fragment)
            .commit()
    }

    override fun onButtonClicked(cat: Cat) {
        bottomNavView.visibility = View.GONE
        loadFragmentWithId(cat, DetailsFragment())
    }

    private fun loadFragmentWithId(cat: Cat, fragment: Fragment) {
        fragment.arguments = bundleOf("cat" to cat)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onClosePressed() {
        bottomNavView.visibility = View.VISIBLE
        supportFragmentManager.popBackStack()
    }
}
