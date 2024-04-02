package com.example.dnd.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dnd.R
import com.example.dnd.StartScreen
import com.example.dnd.MainActivity
import com.example.dnd.data.DataStoreUtil
import com.example.dnd.data.ThemeViewModel
import com.example.dnd.ui.AppViewModelProvider
import com.example.dnd.ui.HomebrewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun ProfileScreen(
    onConfirmPressed: (Int) -> Unit,
    dataStoreUtil: DataStoreUtil, themeViewModel: ThemeViewModel,
    mainActivity: MainActivity, navController: NavHostController = rememberNavController(),
    homebrewViewModel: HomebrewViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.TopCenter)

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        Text(text = homebrewViewModel.getUsername(), style = MaterialTheme.typography.h5)

        ProfilePicture(homebrewViewModel)

        Button(onClick = {
            requestCameraPermission(mainActivity, homebrewViewModel)

            if (homebrewViewModel.getIsCameraPermitted()) {
                navController.navigate(StartScreen.Camera.name)
            }
        }) {
            Image(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(R.drawable.photo_camera_48px),
                contentDescription = "Camera"
            )
        }
        DarkModeSwitch(dataStoreUtil = dataStoreUtil, themeViewModel = themeViewModel)
        Button(onClick = { onConfirmPressed(0) }) {
            Text(text = stringResource(R.string.confirm), color = MaterialTheme.colors.secondary)
        }
        Button(onClick = { onConfirmPressed(1) }) {
            Text(text = stringResource(R.string.log_out), color = MaterialTheme.colors.secondary)
        }
    }
}

@Composable
fun DarkModeSwitch(dataStoreUtil: DataStoreUtil, themeViewModel: ThemeViewModel) {
    var switchState by remember {themeViewModel.isDarkThemeEnabled}
    val coroutineScope = rememberCoroutineScope()
    Log.d("SWITCH", switchState.toString())

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.dark_mode))
        Switch(
            checked = switchState,
            onCheckedChange = {
                switchState = it

                coroutineScope.launch {
                    dataStoreUtil.saveTheme(it)
                }
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colors.primary,
                checkedThumbColor = MaterialTheme.colors.onPrimary,
            )
        )
    }
}

@Composable
fun ProfilePicture(homebrewViewModel: HomebrewViewModel) {
    val imageBitmap = homebrewViewModel.getProfilePicture()
        ?.let { rememberImageBitmapFromStorage(it) }

    val mod = Modifier.size(72.dp).clip(CircleShape)

    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap,
            contentDescription = stringResource(R.string.profile),
            modifier = mod
        )
    } else {
        Image(
            painter = painterResource(R.drawable.person_48px),
            contentDescription = stringResource(R.string.profile),
            modifier = mod
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun rememberImageBitmapFromStorage(imagePath: String): ImageBitmap? {
    val imageBitmapState = remember { mutableStateOf<ImageBitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        val bitmap = loadImageBitmapFromStorage(imagePath)
        imageBitmapState.value = bitmap
    }

    return imageBitmapState.value
}

suspend fun loadImageBitmapFromStorage(imagePath: String): ImageBitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val file = File(imagePath)
            val bitmap: Bitmap? = BitmapFactory.decodeFile(file.path)
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

fun requestCameraPermission(mainActivity: MainActivity, homebrewViewModel: HomebrewViewModel) {
    when {
        ContextCompat.checkSelfPermission(
            mainActivity.applicationContext,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED -> {
            Log.i("kilo", "Permission previously granted")
            homebrewViewModel.setIsCameraPermitted(true)
        }

        ActivityCompat.shouldShowRequestPermissionRationale(
            mainActivity,
            Manifest.permission.CAMERA
        ) -> Log.i("kilo", "Show camera permissions dialog")

        else -> mainActivity.requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }
}



