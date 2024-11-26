package com.alican.multimodulewithkoin.ui

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.serialization.Serializable


@Serializable
data object HomeScreenRoute

@Serializable
data object PointsShopScreenRoute

@Serializable
data object WalletScreenRoute

@Serializable
data object QrScreenRoute


@Composable
fun AppBottomBar(modifier: Modifier = Modifier, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val items = listOf(
        BottomBarRoute(
            name = "Ana Sayfa",
            route = HomeScreenRoute,
            unSelectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home
        ),
        BottomBarRoute(
            name = "Puan Dükkanı",
            route = PointsShopScreenRoute,
            unSelectedIcon = Icons.Outlined.Search,
            selectedIcon = Icons.Filled.Search
        ),
        BottomBarRoute(
            name = "Cüzdan",
            route = WalletScreenRoute,
            unSelectedIcon = Icons.Outlined.Search,
            selectedIcon = Icons.Filled.Search
        ),
        BottomBarRoute(
            name = "Şifre Gir",
            route = QrScreenRoute,
            unSelectedIcon = Icons.Outlined.Search,
            selectedIcon = Icons.Filled.Search
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color.White),
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth(),
            windowInsets = WindowInsets(left = 20.dp, right = 20.dp),
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    interactionSource = NoRippleInteractionSource,
                    selected = currentDestination?.route == item.route,
                    onClick = {
//                    navController.navigate(item.route) {
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
                    },
                    icon = {
                        Icon(
                            item.selectedIcon,
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    label = {
                        Text(item.name, fontSize = 9.sp)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = Color.Black,
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )

                )
            }

        }
    }
}

private object NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction>
        get() = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction): Boolean = true

}


data class BottomBarRoute<T : Any>(
    val name: String,
    val route: T,
    val unSelectedIcon: ImageVector,
    val selectedIcon: ImageVector
)
