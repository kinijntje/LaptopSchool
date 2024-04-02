package com.example.dnd

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dnd.data.DataStoreUtil
import com.example.dnd.data.DnDUiState
import com.example.dnd.data.ThemeViewModel
import com.example.dnd.ui.*
import com.example.dnd.ui.navigation.DnDNavHost
import com.example.dnd.ui.theme.DnDTheme
import kotlinx.coroutines.launch

enum class StartScreen(@StringRes val title: Int) {
    Login(title = R.string.login),
    SignUp(title = R.string.signup),
    Home(title = R.string.home),
    Profile(title = R.string.profile),
    Filter(title = R.string.filter),
    Homebrew(title = R.string.homebrew),
    Agenda(title = R.string.agenda),
    Games(title = R.string.games),
    Spells(title = R.string.spells),
    Classes(title = R.string.classes),
    Races(title = R.string.races),
    Feats(title = R.string.feats),
    Backgrounds(title = R.string.backgrounds),
    Items(title = R.string.items),
    Camera(title = R.string.camera)

}

@Composable
fun DnDTopAppBar(currentScreen: StartScreen, scaffoldState: ScaffoldState, navHostController: NavHostController, homebrewViewModel: HomebrewViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val coroutineScope = rememberCoroutineScope()
    Row(modifier = Modifier
        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            if (currentScreen == StartScreen.Home) {
                coroutineScope.launch { scaffoldState.drawerState.open() }
            }
            else {
                navHostController.popBackStack()
            }
        }, elevation = null, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {

            if (currentScreen == StartScreen.Home) {
                Image(painter = painterResource(R.drawable.menu_48px), contentDescription = "menu picture", modifier = Modifier
                    .height(40.dp)
                    .padding(start = 10.dp))
            }
            else {
                Image(painter = painterResource(R.drawable.arrow_back_48px), contentDescription = "menu picture", modifier = Modifier
                    .height(40.dp)
                    .padding(start = 10.dp))
            }
        }
        if (currentScreen != StartScreen.Camera) {
            Text(text = stringResource(R.string.user, homebrewViewModel.getUsername()), modifier = Modifier.padding(25.dp), color = MaterialTheme.colors.secondary)
        }
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)) {
        if (currentScreen != StartScreen.Camera) {
            Text(text = stringResource(currentScreen.title), style = MaterialTheme.typography.h1, modifier = Modifier.padding(top = 80.dp, bottom = 30.dp), color = MaterialTheme.colors.secondary)
        }
    }
}

@Composable
fun DnDSideMenu(onIconPressed: (Int) -> Unit, scaffoldState: ScaffoldState, currentScreen: StartScreen) {
    SideMenuItem(currentScreen,StartScreen.Home, onIconPressed = onIconPressed, 0, R.string.home)
    SideMenuItem(currentScreen,StartScreen.Games, onIconPressed = onIconPressed, 1, R.string.games)
    SideMenuItem(currentScreen,StartScreen.Agenda, onIconPressed = onIconPressed, 2, R.string.agenda)
    SideMenuItem(currentScreen,StartScreen.Profile, onIconPressed = onIconPressed, 3, R.string.profile)
}

@Composable
fun SideMenuItem(currentScreen: StartScreen, startScreen: StartScreen, onIconPressed: (Int) -> Unit, i: Int, textRes: Int) {
    Row(
        modifier = Modifier
            .background(if (currentScreen == startScreen) Color.Black else MaterialTheme.colors.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(onClick = { onIconPressed(i) }, modifier = Modifier
            .height(50.dp)
            .weight(1f), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), elevation = null)
        {
            Text(
                modifier = Modifier
                    .padding(start = 24.dp),
                text = stringResource(textRes),
            )
        }
    }
}

@Composable
fun DnDBottomAppBar(onIconPressed: (Int) -> Unit, currentScreen: StartScreen) {
    Box(Modifier.background(MaterialTheme.colors.primaryVariant)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically
        ){
            Button(onClick = { onIconPressed(0) }, modifier = Modifier
                .height(50.dp)
                .weight(1f), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), elevation = null) {
                Image(painter = painterResource(R.drawable.home_48px), contentDescription = "home", modifier = Modifier.height(50.dp), colorFilter = ColorFilter.tint(if (currentScreen == StartScreen.Home) MaterialTheme.colors.secondary else Color.White))
            }
            Button(onClick = { onIconPressed(1) }, modifier = Modifier
                .height(50.dp)
                .weight(1f), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), elevation = null) {
                Image(painter = painterResource(R.drawable.videogame_asset_48px), contentDescription = "games", modifier = Modifier.height(50.dp), colorFilter = ColorFilter.tint(if (currentScreen == StartScreen.Games) MaterialTheme.colors.secondary else Color.White))
            }
            Button(onClick = { onIconPressed(2) }, modifier = Modifier
                .height(50.dp)
                .weight(1f), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), elevation = null) {
                Image(painter = painterResource(R.drawable.event_note_48px), contentDescription = "planner", modifier = Modifier.height(50.dp), colorFilter = ColorFilter.tint(if (currentScreen == StartScreen.Agenda) MaterialTheme.colors.secondary else Color.White))
            }
            Button(onClick = { onIconPressed(3) }, modifier = Modifier
                .height(50.dp)
                .weight(1f), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), elevation = null) {
                Image(painter = painterResource(R.drawable.account_circle_48px), contentDescription = "user", modifier = Modifier.height(50.dp), colorFilter = ColorFilter.tint(if (currentScreen == StartScreen.Profile) MaterialTheme.colors.secondary else Color.White))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(modifier: Modifier = Modifier, viewModel: HomebrewViewModel = viewModel(), mainActivity: MainActivity, navController: NavHostController = rememberNavController(), context: Context) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = StartScreen.valueOf(
        backStackEntry?.destination?.route ?: StartScreen.Home.name
    )
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val dataStoreUtil = DataStoreUtil(context)
    val themeViewModel = ThemeViewModel()

    val darkTheme = dataStoreUtil.getTheme(false).collectAsState(initial = false)
    themeViewModel.setTheme(darkTheme.value)

    DnDTheme(darkTheme.value) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                when (currentScreen) {
                    StartScreen.Login, StartScreen.SignUp -> null
                    else -> DnDTopAppBar(currentScreen, scaffoldState, navController)
                }
            },
            drawerContent = {
                DnDSideMenu(onIconPressed = {
                    when (it) {
                        0 -> { navController.navigate(StartScreen.Home.name) }
                        1 -> { navController.navigate(StartScreen.Games.name) }
                        2 -> { navController.navigate(StartScreen.Agenda.name) }
                        else -> { navController.navigate(StartScreen.Profile.name) }
                    }
                    coroutineScope.launch { scaffoldState.drawerState.close() }
                    },
                    scaffoldState = scaffoldState, currentScreen = currentScreen)
            },
            drawerGesturesEnabled = true,
            bottomBar = {
                when (currentScreen) {
                    StartScreen.Login, StartScreen.SignUp, StartScreen.Camera -> null
                    else -> DnDBottomAppBar(
                        onIconPressed = {
                            when (it) {
                                0 -> { navController.navigate(StartScreen.Home.name) }
                                1 -> { navController.navigate(StartScreen.Games.name) }
                                2 -> { navController.navigate(StartScreen.Agenda.name) }
                                else -> { navController.navigate(StartScreen.Profile.name) }
                            }
                        }, currentScreen
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.background
        ) { innerPadding ->
            val uiState by viewModel.uiState.collectAsState()

            DnDNavHost(navController = navController, modifier.padding(innerPadding), mainActivity = mainActivity, homebrewViewModel = viewModel, dataStoreUtil = dataStoreUtil, themeViewModel = themeViewModel, context = context)
        }
    }
}

@Composable
fun MainPage(onButtonPressed: (Int) -> Unit) {
    val modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        BigButtonRow(stringResource(R.string.classes), stringResource(R.string.spells), onButtonPressed, 0)
        Spacer(modifier = Modifier.height(30.dp))
        BigButtonRow(stringResource(R.string.races), stringResource(R.string.feats), onButtonPressed, 2)
        Spacer(modifier = Modifier.height(30.dp))
        BigButtonRow(stringResource(R.string.backgrounds), stringResource(R.string.items), onButtonPressed, 4)
    }
}

@Composable
fun BigButtonRow(text1: String, text2: String, onButtonPressed: (Int) -> Unit, indexAid: Int) {

    Row(horizontalArrangement = Arrangement.SpaceAround) {
        Button(modifier = Modifier.size(130.dp), onClick = { onButtonPressed(indexAid) }) {
            Text(text = text1, color = MaterialTheme.colors.secondary)
        }
        Spacer(modifier = Modifier.width(30.dp))
        Button(modifier = Modifier.size(130.dp), onClick = { onButtonPressed(indexAid + 1) }) {
            Text(text = text2, color = MaterialTheme.colors.secondary)
        }
    }
}