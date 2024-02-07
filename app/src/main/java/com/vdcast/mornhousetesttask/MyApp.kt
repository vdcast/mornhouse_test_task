package com.vdcast.mornhousetesttask

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vdcast.mornhousetesttask.di.appModule
import com.vdcast.mornhousetesttask.ui.AppViewModel
import com.vdcast.mornhousetesttask.ui.navigation.Routes
import com.vdcast.mornhousetesttask.ui.screens.details.Details
import com.vdcast.mornhousetesttask.ui.screens.home.Home
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.koinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class MyApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(appModule)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUi(
    navController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel = koinViewModel()
) {
    val uiState by appViewModel.uiState.collectAsState()
    val snackbarHostState = remember {SnackbarHostState() }

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { contentPadding ->
            NavHost(
                modifier = Modifier.padding(contentPadding),
                navController = navController,
                startDestination = Routes.Home.route
            ) {
                composable(Routes.Home.route) {
                    Home(
                        uiState = uiState,
                        onEvent = appViewModel::onEvent,
                        navigateToDetailsScreen = {
                            navController.navigate(Routes.Details.route)
                        },
                        snackbarHostState = snackbarHostState
                    )
                }
                composable(Routes.Details.route) {
                    Details(
                        selectedFact = uiState.selectedFact,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
}