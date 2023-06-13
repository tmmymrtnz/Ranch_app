package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController( )
                Scaffold(
                    bottomBar = {
                        BotNavBar(
                            items = listOf(
                                BotNavItem(
                                    name="Home",
                                    route= "home",
                                    icon = painterResource(id = R.drawable.home),
                                ),
                                BotNavItem(
                                    name="Devices",
                                    route= "devices",
                                    icon = painterResource(id = R.drawable.devices),
                                ),
                                BotNavItem(
                                    name="Routines",
                                    route= "routines",
                                    icon = painterResource(id = R.drawable.clock_outline),
                                ),
                                BotNavItem(
                                    name="Settings",
                                    route= "settings",
                                    icon = painterResource(id = R.drawable.cog_outline),
                                ),

                                ),
                            navController = navController ,
                        ) {
                            navController.navigate(it.route)
                        }
                    }
                ) {
                    Box(Modifier.padding(bottom = it.calculateBottomPadding())) {
                        Navigation(navController = navController)
                    }
                }
            }
        }
    }

}

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = "home" ){
        composable("home"){
            MyScreenComponent(navController = navController)
        }
        composable("devices"){
            DevicesScreen(navController = navController)
        }
        composable("routines"){
            LightBulbScreen(lightViewModel = viewModel())
            //TODO hecerlo con las screen bien
        }
        composable("settings"){
            ovenScreen(ovenViewModel = viewModel())
            //TODO hecerlo con las screen bien
        }
        composable("oven"){
            ovenScreen(ovenViewModel = viewModel())
        }
        composable("light bulb"){
            LightBulbScreen(lightViewModel = viewModel())
        }
        composable("fridge"){
            fridgeScreen(fridgeViewModel = viewModel())
        }
        composable("curtain"){
           //TODO no esta la screen hecha
        }
        composable("speaker"){
            //TODO no esta la screen hecha
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BotNavBar(
    items: List<BotNavItem>,
    navController: NavController,
    onItemClick: (BotNavItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(

        containerColor = Color.White
    ){
      items.forEach{
          item ->
          val selected = item.route == backStackEntry.value?.destination?.route
          NavigationBarItem(
          selected = selected,
          onClick = {onItemClick(item)},
              colors = NavigationBarItemDefaults.colors(
                  selectedIconColor = Color.White,
                  unselectedIconColor = MaterialTheme.colorScheme.tertiary,
                  indicatorColor = MaterialTheme.colorScheme.tertiary
              ),
          icon = {
              Column(horizontalAlignment= CenterHorizontally) {
                  if(item.badgeCount>0){
                      BadgedBox(
                          badge = { Badge { Text(item.badgeCount.toString()) } }
                      ){
                          Icon(
                              painter = item.icon,
                              contentDescription = null,
                              modifier = Modifier.size(40.dp)
                          )
                      }
                  }else{
                      Icon(
                          painter = item.icon,
                          contentDescription = null,
                      )
                  }
                  if(selected){
                      Text(
                          text = item.name,
                      )
                  }
              }
          }
          )
      }
    }
}

@Composable
fun RoundedCardComponent(
    title: String,
    icon: Painter,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Button(onClick = {  if(title == "Oven"){
        navController.navigate("oven")
    }else if(title == "Curtain"){
        navController.navigate("curtain")
    }else if(title == "Light Bulb"){
        navController.navigate("light bulb")
    }else if(title == "Fridge"){
        navController.navigate("fridge")
    }else if(title == "Speaker"){
        navController.navigate("speaker")
    } },
        modifier = Modifier.padding(4.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .padding(10.dp),
        contentPadding = PaddingValues(4.dp),
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
        }
    )
}

@Composable
fun MyScreenComponent(
    modifier: Modifier = Modifier,
    navController: NavController,
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
                        icon = item.icon,
                        navController =  navController
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


