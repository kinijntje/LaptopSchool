package com.example.dnd.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.dnd.R
import com.example.dnd.api.ApiManager
import com.example.dnd.api.ErrorResponse
import com.example.dnd.api.PostData
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignUpScreen(onButtonPressed: () -> Unit, onRegisterPressed: (String) -> Unit) {
    val modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)

    val focusManager = LocalFocusManager.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }

    fun registerUser() {

        val postData = PostData(username, password)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiManager.apiService.registerUser(postData)
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    Log.d("RESPONSE", registerResponse.toString())
                    if (registerResponse != null) {
                        onRegisterPressed(registerResponse.username)
                    }
                    // Handle successful response
                } else {
                    val errorResponse = response.errorBody()?.string()
                    val error = Gson().fromJson(errorResponse, ErrorResponse::class.java)
                    errorMessage = error?.error ?: "Unknown error"
                    showError = true
                }
                // Handle the response here
            } catch (e: Exception) {
                // Handle the exception here
                errorMessage = "failed to connect"
                showError = true
                Log.d("RESPONSE_ERROR", e.message.toString())
                e.printStackTrace()
            }
        }
    }


    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        EditInputField(
            label = R.string.username,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = username,
            onValueChanged = { username = it }
        )
        Spacer(modifier = Modifier.height(15.dp))

        EditInputField(
            label = R.string.password,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = password,
            onValueChanged = { password = it },
            isPassword = true
        )
        Spacer(modifier = Modifier.height(15.dp))

        EditInputField(
            label = R.string.confirm_password,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.clearFocus() }
            ),
            value = confirm,
            onValueChanged = { confirm = it },
            isPassword = true
        )
        Spacer(modifier = Modifier.height(15.dp))

        Row {
            Button(onClick = { onButtonPressed() }) {
                Text(text = stringResource(R.string.cancel), color = MaterialTheme.colors.secondary)
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = { if (registerCheck(username, password, confirm)) {
                                    registerUser()
                                }
                                else {
                                    errorMessage = "All fields must at least have 2 characters and confirm password must be the same as password"
                                    showError = true
                                }
            }) {
                Text(text = stringResource(R.string.sign_up), color = MaterialTheme.colors.secondary)
            }
        }


    }

    //animation
    Column(modifier = Modifier
        .padding(top = 520.dp, start = 30.dp, end = 30.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(showError) {
            if (showError) {
                delay(3000) // Delay for 3 seconds
                showError = false // Reset showError to false
            }
        }

        AnimatedVisibility(
            visible = showError,
            enter = slideInVertically(initialOffsetY = { -40 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -40 }) + fadeOut()
        ) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .wrapContentSize(Alignment.BottomCenter)
                    .background(MaterialTheme.colors.onSurface)
            )
        }
    }
}

fun registerCheck(username: String, password: String, confirm: String): Boolean {
    return username.length >= 2 && password.length >= 2 && password == confirm
}

