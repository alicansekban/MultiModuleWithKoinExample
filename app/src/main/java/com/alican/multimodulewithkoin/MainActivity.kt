package com.alican.multimodulewithkoin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alican.multimodulewithkoin.ui.AppBottomBar
import com.alican.multimodulewithkoin.ui.home.HomeScreen
import com.alican.multimodulewithkoin.ui.theme.MultiModuleWithKoinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiModuleWithKoinTheme {

                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "home"
                        ) {
                            composable("home") {
                                HomeScreen()

                            }
                            composable("detail") {
//                                DetailScreen {
//                                    navController.popBackStack()
//                                }
                            }
                        }
                        AppBottomBar(
                            navController = navController, modifier = Modifier.align(
                                Alignment.BottomCenter
                            )
                        )

                    }
                }
            }
        }
    }
}