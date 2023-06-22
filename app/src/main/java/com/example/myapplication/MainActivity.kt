package com.example.myapplication

import android.annotation.SuppressLint
import android.Manifest
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.blind.BlindScreen
import com.example.myapplication.blind.BlindViewModel
import com.example.myapplication.data.network.model.Result
import com.example.myapplication.devices.DeviceViewModel
import com.example.myapplication.devices.DevicesScreen
import com.example.myapplication.fridge.FridgeViewModel
import com.example.myapplication.lamp.LightBulbScreen
import com.example.myapplication.lamp.LightViewModel
import com.example.myapplication.oven.OvenViewModel
import com.example.myapplication.routines.RoutinesScreen
import com.example.myapplication.speaker.SpeakerViewModel
import com.example.myapplication.speaker.speakerScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


class MainActivity : ComponentActivity() {
    private lateinit var receiver: SkipNotificationReceiver
    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
            val isLargeTablet = LocalConfiguration.current.screenWidthDp >= 600  // consider a device with >= 600dp width as a large device.

            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val permissionState =
                            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
                        if (!permissionState.hasPermission) {
                            NotificationPermission(permissionState = permissionState)
                            LaunchedEffect(true) {
                                permissionState.launchPermissionRequest()
                            }
                        }
                    }

                    val deviceId = intent?.getStringExtra(MyIntent.DEVICE_ID)
                    if (deviceId != null) {
                        Text(
                            text = "oops..."
                        )
                    }
                    Box(Modifier.padding(bottom = it.calculateBottomPadding())) {
                        if (isLandscape || isLargeTablet) {
                            Row(Modifier.fillMaxSize()) {
                                SideNavBar(
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
                                    navController = navController,
                                    onItemClick = { navController.navigate(it.route) }
                                )

                                Box(Modifier.weight(1f)) {
                                    Navigation(navController = navController)
                                }
                            }
                        } else {
                            Column(Modifier.fillMaxSize()) {
                                Box(Modifier.weight(1f)) {
                                    Navigation(navController = navController)
                                }

                                BotNavBar(
                                    items = listOf(
                                        BotNavItem(
                                            name= stringResource(id = R.string.home),
                                            route= "home",
                                            icon = painterResource(id = R.drawable.home),
                                        ),
                                        BotNavItem(
                                            name= stringResource(id = R.string.devices),
                                            route= "devices",
                                            icon = painterResource(id = R.drawable.devices),
                                        ),
                                        BotNavItem(
                                            name= stringResource(id = R.string.routines),
                                            route= "routines",
                                            icon = painterResource(id = R.drawable.clock_outline),
                                        ),
                                        BotNavItem(
                                            name= stringResource(id = R.string.settings),
                                            route= "settings",
                                            icon = painterResource(id = R.drawable.cog_outline),
                                        ),
                                    ),
                                    navController = navController,
                                    onItemClick = { navController.navigate(it.route) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    override fun onResume() {
        MyIntent.notif = true
        receiver = SkipNotificationReceiver( notif = MyIntent.notif )
        IntentFilter(MyIntent.SHOW_NOTIFICATION)
            .apply { priority = 1 }
            .also { registerReceiver(receiver, it) }
        super.onResume()
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun NotificationPermission(
        permissionState: PermissionState,
    ) {
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = { /* TODO: función para infromarle al usuario de la necesidad de otrogar el permiso */ },
            permissionNotAvailableContent = { /* TODO: función hacer las adecuaciones a la App debido a que el permiso no fue otorgado  */ }
        ) {
            /* Hacer uso del recurso porque el permiso fue otorgado */
        }
    }

    override fun onStop() {
        super.onStop()
        MyIntent.notif = false
        unregisterReceiver(receiver)
    }

}


@SuppressLint("SuspiciousIndentation")
@Composable
fun Navigation(navController: NavHostController){

    NavHost(navController = navController, startDestination = "home" ){
        composable("home"){
            MyScreenComponent(homeViewModel = viewModel(), navController = navController)
        }
        composable("devices"){
            DevicesScreen(viewModel = viewModel(), typesViewModel = viewModel(), navController = navController)
        }
        composable("routines"){
            RoutinesScreen(routineViewModel = viewModel())
        }
        composable("settings"){
            SettingsScreen()
        }
        composable(route = "oven/{id}",
            arguments = listOf(
                navArgument("id"){type = NavType.StringType}
            )
        ){ entry ->
            val id = entry.arguments?.getString("id") ?: "no id"
            val ovVM = OvenViewModel()
            ovVM.fetchADevice(id)
            ovenScreen(id = id, ovenViewModel = ovVM)
        }
        composable("light bulb/{id}", arguments = listOf(
            navArgument("id"){type = NavType.StringType}
        )
        ){ entry ->
            val id = entry.arguments?.getString("id") ?: "no id"
            val lbVM = LightViewModel()
            lbVM.fetchADevice(id)
            LightBulbScreen(id = id ,lightViewModel = lbVM)
        }
        composable("fridge/{id}",
            arguments = listOf(
                navArgument("id"){type = NavType.StringType}
            )
        ){entry ->
            val id = entry.arguments?.getString("id") ?: "no id"
            val fdVM = FridgeViewModel()
            fdVM.fetchADevice(id)
            fridgeScreen(id = id,fridgeViewModel = fdVM)
        }
        composable("curtain/{id}",
            arguments = listOf(
                navArgument("id"){type = NavType.StringType}
            )
            ){ entry ->
            val id = entry.arguments?.getString("id") ?: "no id"
            val blVM = BlindViewModel()
            blVM.fetchADevice(id)
            BlindScreen(id = id, blindViewModel = blVM)
        }
        composable("speaker/{id}",
            arguments = listOf(
                navArgument("id"){type = NavType.StringType}
            )
        ){entry ->
            val id = entry.arguments?.getString("id") ?: "no id"
            val spVM = SpeakerViewModel()
            spVM.fetchADevice(id)
            speakerScreen(id, spVM)
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

        containerColor = MaterialTheme.colorScheme.secondary
    ){
      items.forEach{
          item ->
          val selected = item.route == backStackEntry.value?.destination?.route
          NavigationBarItem(
          selected = selected,
          onClick = {onItemClick(item)},
              colors = NavigationBarItemDefaults.colors(
                  selectedIconColor = MaterialTheme.colorScheme.secondary,
                  unselectedIconColor = MaterialTheme.colorScheme.tertiaryContainer,
                  indicatorColor = MaterialTheme.colorScheme.tertiaryContainer
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
    result: Result,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Button(onClick = {
        val id:String =result.id.toString()
        when (result.type?.name.toString()) {
            "refrigerator" -> navController.navigate("fridge/$id")
            "speaker" -> navController.navigate("speaker/$id")
            "oven" -> navController.navigate("oven/$id")
            "lamp" -> navController.navigate("light bulb/$id")
            "blinds" -> navController.navigate("curtain/$id")
            else -> navController.navigate("home")
        } },
        modifier = Modifier
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .padding(10.dp),
        contentPadding = PaddingValues(4.dp),
    ) {

            Row(
                modifier = Modifier.padding(5.dp),
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
                    color = MaterialTheme.colorScheme.scrim
                )

            }
        }

}

@Composable
fun MyScreenComponent(
    modifier: Modifier = Modifier,
    homeViewModel: DeviceViewModel = viewModel(),
    navController: NavController,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by homeViewModel.uiState.collectAsState()
    val groupedDevices = uiState.devices?.result?.groupBy { it.room?.name }

    LaunchedEffect(Unit){
        homeViewModel.fetchDevices()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = {
                homeViewModel.fetchDevices()
            },
            modifier = modifier.padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
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
                            color = MaterialTheme.colorScheme.scrim,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        groupedDevices?.forEach { (room, devices) ->
                            item {
                                Text(
                                    text = room ?: "Not in room",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.scrim,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                Divider(color = MaterialTheme.colorScheme.secondary)
                                Spacer(modifier = Modifier.height(8.dp))
                                LazyRow {
                                    items(items = devices){device ->
                                        RoundedCardComponent(
                                            title = device.name ?: "",
                                            result = device,
                                            icon = painterResource(id =
                                            when (device.type?.name.toString()) {
                                                "fridge" -> R.drawable.fridge
                                                "speaker" -> R.drawable.speaker
                                                "oven" -> R.drawable.stove
                                                "lamp" -> R.drawable.lightbulb
                                                "blinds" -> R.drawable.curtains
                                                else -> R.drawable.fridge
                                            }),
                                            navController =  navController
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideNavBar(
    items: List<BotNavItem>,
    navController: NavController,
    onItemClick: (BotNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val selectedColor = MaterialTheme.colorScheme.tertiary
    val unselectedColor = MaterialTheme.colorScheme.tertiary

    Column(
        modifier = Modifier
            .width(100.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

            val itemColor = if (selected) MaterialTheme.colorScheme.secondary else unselectedColor

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .background(
                        if (selected) selectedColor else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (item.badgeCount > 0) {
                        BadgedBox(
                            badge = { Badge { Text(item.badgeCount.toString()) } },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Icon(
                                painter = item.icon,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = itemColor
                            )
                        }
                    } else {
                        Icon(
                            painter = item.icon,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = itemColor
                        )
                    }
                    if (selected) {
                        Text(
                            text = item.name,
                            color = itemColor,
                            style = MaterialTheme.typography.headlineSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

