package com.example.myapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreenComponent()
                }
            }
        }
    }
}


@Composable
fun RoundedCardComponent(
    title: String,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = icon,
                contentDescription = "",
                Modifier.size(50.dp)
            )
             Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            IconActionButton(onClick = { })
        }

    }
}



data class CardItem(
    val title: String,
    val icon: Painter,
)




@Composable
fun IconActionButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(4.dp),
        contentPadding = PaddingValues(4.dp),
        content = {
            Icon(
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        },

    )
}


@Composable
fun MyScreenComponent(
    modifier: Modifier = Modifier
) {
    val cardList = listOf(
        CardItem(stringResource(id = R.string.fridge), painterResource(id = R.drawable.fridge)),
        CardItem(stringResource(id = R.string.speaker), painterResource(id = R.drawable.speaker)),
        CardItem(stringResource(id = R.string.stove), painterResource(id = R.drawable.stove)),
        CardItem(stringResource(id = R.string.lightBulb), painterResource(id = R.drawable.lightbulb)),
        CardItem(stringResource(id = R.string.curtain), painterResource(id = R.drawable.curtains))

    )


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_notext),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
                items(items = cardList){item ->
                    RoundedCardComponent(
                        title = item.title,
                        icon = item.icon
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }
    }
}






@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {

    MyScreenComponent()


    }
}



