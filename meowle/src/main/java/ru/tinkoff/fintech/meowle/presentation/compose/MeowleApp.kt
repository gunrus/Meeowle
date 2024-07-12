package ru.tinkoff.fintech.meowle.presentation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.DefaultFadingTransitions
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.navgraphs.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination
import ru.tinkoff.fintech.meowle.presentation.compose.ui.bottom.MeowleBottomBar
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.top.MeowleTopAppBar

/**
 * @author Ruslan Ganeev
 */
@Composable
fun MeowleApp() {
    MeowleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            val currentDestination: DestinationSpec = navController.currentDestinationAsState().value ?: NavGraphs.root.startDestination

            Scaffold(
                topBar = {
                         MeowleTopAppBar(
                             currentDestination = currentDestination
                         ) {
                             navController.navigateUp()
                         }
                },
                bottomBar = {
                    MeowleBottomBar(currentDestination) {
                        navController.navigate(it) {
                            popUpTo(RootNavGraph.startDestination) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                }
            ) { paddingValues ->
                DestinationsNavHost(
                    navGraph = RootNavGraph,
                    navController = navController,
                    defaultTransitions = DefaultFadingTransitions,
                    modifier = Modifier
                        .padding(paddingValues),
                )
            }
        }
    }
}
