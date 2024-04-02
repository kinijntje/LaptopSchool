package com.example.dnd.ui.navigation

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dnd.MainActivity
import com.example.dnd.MainPage
import com.example.dnd.StartScreen
import com.example.dnd.data.DataStoreUtil
import com.example.dnd.data.ThemeViewModel
import com.example.dnd.ui.screens.CameraScreen
import com.example.dnd.ui.screens.AgendaScreen
import com.example.dnd.ui.screens.BackgroundScreen
import com.example.dnd.ui.screens.ClassesScreen
import com.example.dnd.ui.screens.FeatScreen
import com.example.dnd.ui.screens.GamesScreen
import com.example.dnd.ui.screens.HomebrewScreen
import com.example.dnd.ui.HomebrewViewModel
import com.example.dnd.ui.screens.ItemScreen
import com.example.dnd.ui.screens.LoginPage
import com.example.dnd.ui.screens.ProfileScreen
import com.example.dnd.ui.screens.RaceScreen
import com.example.dnd.ui.screens.SignUpScreen
import com.example.dnd.ui.screens.SpellFilterPage
import com.example.dnd.ui.screens.SpellScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.Executors

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun DnDNavHost(navController: NavHostController, modifier: Modifier = Modifier, mainActivity: MainActivity, homebrewViewModel: HomebrewViewModel, dataStoreUtil: DataStoreUtil, themeViewModel: ThemeViewModel, context: Context) {
    NavHost(navController = navController, startDestination = StartScreen.Login.name) {
        composable(route = StartScreen.Login.name) {
            LoginPage(
                onButtonPressed = {
                    when (it) {
                        0 -> { navController.navigate(StartScreen.SignUp.name) }
                        else -> { navController.navigate(StartScreen.Home.name) }
                    }
                },
                onLoginPressed = {
                    homebrewViewModel.setUsername(it)
                    navController.navigate(StartScreen.Home.name)
                }
            )
        }
        composable(route = StartScreen.SignUp.name) {
            SignUpScreen(
                onButtonPressed = { navController.navigate(StartScreen.Login.name) },
                onRegisterPressed = {
                    homebrewViewModel.setUsername(it)
                    navController.navigate(StartScreen.Home.name)
                }
            )
        }
        composable(route = StartScreen.Home.name) {
            MainPage(
                onButtonPressed = {
                    when (it) {
                        0 -> { navController.navigate(StartScreen.Classes.name)}
                        1 -> { navController.navigate(StartScreen.Spells.name)}
                        2 -> { navController.navigate(StartScreen.Races.name)}
                        3 -> { navController.navigate(StartScreen.Feats.name)}
                        4 -> { navController.navigate(StartScreen.Backgrounds.name)}
                        else -> { navController.navigate(StartScreen.Items.name)}
                    }
                }
            )
        }
        composable(route = StartScreen.Profile.name) {
            ProfileScreen(
                onConfirmPressed = {
                    when (it) {
                        0 -> navController.navigate(StartScreen.Home.name)
                        else -> {
                            homebrewViewModel.setUsername("Offline")
                            navController.navigate(StartScreen.Login.name)
                        }
                    }
                },
                dataStoreUtil = dataStoreUtil,
                themeViewModel = themeViewModel,
                mainActivity = mainActivity,
                navController = navController,
                homebrewViewModel = homebrewViewModel
            )
        }
        composable(route = StartScreen.Filter.name) {
            SpellFilterPage(
                onConfirmPressed = {
                    homebrewViewModel.changeFilterState(it)
                    navController.navigate(StartScreen.Spells.name)
                },
                viewModel = homebrewViewModel
            )
        }
        composable(route = StartScreen.Homebrew.name) {
            HomebrewScreen( onSaveSuccessful = { navController.navigate(StartScreen.Spells.name) } )
        }
        composable(route = StartScreen.Agenda.name) {
            AgendaScreen(context = context)
        }
        composable(route = StartScreen.Games.name) {
            GamesScreen()
        }
        composable(route = StartScreen.Spells.name) {
            SpellScreen(
                onFilterPressed = { navController.navigate(StartScreen.Filter.name) },
                onAddPressed = { navController.navigate(StartScreen.Homebrew.name) },
                homebrewViewModel = homebrewViewModel
            )
        }
        composable(route = StartScreen.Classes.name) {
            ClassesScreen()
        }
        composable(route = StartScreen.Races.name) {
            RaceScreen()
        }
        composable(route = StartScreen.Feats.name) {
            FeatScreen()
        }
        composable(route = StartScreen.Backgrounds.name) {
            BackgroundScreen()
        }
        composable(route = StartScreen.Items.name) {
            ItemScreen()
        }
        composable(route = StartScreen.Camera.name) {
            CameraScreen(outputDirectory = mainActivity.getOutputDirectory(), executor = Executors.newSingleThreadExecutor(), onError = { Log.e("kilo", "View error:", it) }, onImageCaptured =  {
                homebrewViewModel.setProfilePicture(it)
                homebrewViewModel.getProfilePicture()?.let { it1 -> Log.e("test", it1) }
            })
        }
    }
}