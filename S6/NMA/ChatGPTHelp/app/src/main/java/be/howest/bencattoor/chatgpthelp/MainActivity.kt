package be.howest.bencattoor.chatgpthelp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import be.howest.bencattoor.chatgpthelp.R
import be.howest.bencattoor.chatgpthelp.ui.theme.ApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val (isDarkMode, setDarkMode) = remember { mutableStateOf(false) }
    ApplicationTheme(isDarkMode) {
        Surface(color = MaterialTheme.colors.background) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(navController) }
                composable("settings") { SettingsScreen(isDarkMode, setDarkMode) }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Home Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("settings") }) {
            Text(text = "Go to Settings")
        }
    }
}

@Composable
fun SettingsScreen(isDarkMode: Boolean, setDarkMode: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Settings Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Switch(
            checked = isDarkMode,
            onCheckedChange = { setDarkMode(!isDarkMode) },
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun DefaultPreview() {
    MyApp()
}

@Composable
fun isSystemInDarkTheme(): Boolean {
    return MaterialTheme.colors.isLight
}

@Composable
fun DrawerButton(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (selected) Color.White else Color.Gray
    val backgroundColor =
        if (selected) colorResource(id = R.color.purple_500) else Color.Transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = textColor
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            color = textColor
        )
    }
}

@Composable
fun DarkModeSwitch() {
    var isDarkTheme by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Dark mode switch",
            tint = if (isDarkTheme) Color.White else Color.Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Dark mode",
            style = MaterialTheme.typography.body1,
            color = if (isDarkTheme) Color.White else Color.Gray
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isDarkTheme,
            onCheckedChange = { isDarkTheme = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.secondary,
                checkedTrackColor = MaterialTheme.colors.primary,
                uncheckedThumbColor = MaterialTheme.colors.secondary,
                uncheckedTrackColor = MaterialTheme.colors.primaryVariant
            )
        )
    }

    ApplicationTheme(isDarkTheme) {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.h4
        )
    }
}

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Settings Screen",
            style = MaterialTheme.typography.h4
        )
    }
}

