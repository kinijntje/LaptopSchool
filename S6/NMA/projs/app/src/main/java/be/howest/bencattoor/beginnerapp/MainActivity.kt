package be.howest.bencattoor.beginnerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import be.howest.bencattoor.beginnerapp.ui.theme.BeginnerAppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeginnerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.DarkGray
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    )
                    {
                        GreetingText(stringResource(R.string.name), "CEO")
                        ExtraInfo(num = "+32 666 666 666", soc = "@twitterstuff", mail = "rando@randomail.com")
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingText(name: String, title: String) {
    val img = painterResource(R.drawable.ic_launcher_foreground)
    Surface(color = Color.Black) {
        Column(modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
            Text(text = "$name", fontSize = 26.sp, color = Color.White)
            Text(
                text = "$title", color = Color.White, modifier = Modifier
                    .padding(top = 6.dp)
            )
        }
    }
}

@Composable
fun ExtraInfo(num: String, soc: String, mail: String) {
    Surface(color = Color.Black, modifier = Modifier.padding(top = 160.dp)) {
        Column(modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            ExtraRow(text = num)
            ExtraRow(text = soc)
            ExtraRow(text = mail)
        }
    }
}

@Composable
fun ExtraRow(text: String) {
    val img = painterResource(R.drawable.ic_launcher_foreground)
    val imageModifier = Modifier
        .size(40.dp)
        .border(BorderStroke(1.dp, Color.Black))
        .background(Color.LightGray)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(painter = img, contentDescription = null, contentScale = ContentScale.Fit, modifier = imageModifier)
        Text(text = text, fontSize = 20.sp, color = Color.White)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardPreview() {
    BeginnerAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.DarkGray
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            )
            {
                GreetingText(stringResource(R.string.name), "CEO")
                ExtraInfo(num = "+32 666 666 666", soc = "@twitterstuff", mail = "rando@randomail.com")
            }
        }
    }
}