package com.alican.multimodulewithkoin

import DetailsContent
import MainContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.navigation.compose.rememberNavController
import com.alican.multimodulewithkoin.ui.theme.MultiModuleWithKoinTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
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
                        var showDetails by remember {
                            mutableStateOf(false)
                        }
                        SharedTransitionLayout {
                            AnimatedContent(
                                targetState = showDetails,
                                label = "basic_transition",
                                transitionSpec = {
                                    (fadeIn(
                                        animationSpec = tween(
                                            durationMillis = 600,
                                            delayMillis = 90
                                        )
                                    ) + // Süreyi 600ms yaparak animasyonu yavaşlattık
                                            scaleIn(
                                                initialScale = 0.92f,
                                                animationSpec = tween(
                                                    durationMillis = 600,
                                                    delayMillis = 90
                                                )
                                            )) // Süreyi 600ms yaparak animasyonu yavaşlattık
                                        .togetherWith(fadeOut(animationSpec = tween(durationMillis = 400)))
                                }
                            ) { targetState ->
                                if (!targetState) {
                                    MainContent(
                                        onShowDetails = { showDetails = true },
                                        animatedVisibilityScope = this@AnimatedContent,
                                        sharedTransitionScope = this@SharedTransitionLayout
                                    )
                                } else {
                                    DetailsContent(
                                        onBack = { showDetails = false },
                                        animatedVisibilityScope = this@AnimatedContent,
                                        sharedTransitionScope = this@SharedTransitionLayout
                                    )
                                }
                            }

                        }

                    }
                }
            }
        }
    }
}

// Animasyon Extension Function
fun Modifier.scaleInOutAnimation(
    duration: Int = 700,
    onAnimationEnd: () -> Unit
): Modifier = composed {
    val transitionState = remember { MutableTransitionState(false) }
    transitionState.targetState = true

    val scale by updateTransition(transitionState, label = "scale")
        .animateFloat(
            transitionSpec = { tween(durationMillis = duration) },
            label = "scaleAnimation"
        ) { state -> if (state) 20f else 0f }

    LaunchedEffect(transitionState) {
        if (transitionState.isIdle && transitionState.targetState) {
            onAnimationEnd()
        }
    }

    this.scale(scale)
}
//
//// Navigation Setup
//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "mainScreen") {
//        composable("mainScreen"
//        ) { MainScreen(navController) }
//        composable("passwordScreen") { PasswordScreen(navController) }
//    }
//}
//
//@Composable
//fun MainScreen(navController: NavController) {
//    var isExpanded by remember { mutableStateOf(false) }
//    var isReturning by remember { mutableStateOf(false) }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        // Şifre Gir Butonu
//        FloatingActionButton(
//            onClick = { isExpanded = true },
//            containerColor = Color.Red,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(16.dp)
//                .size(56.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.PlayArrow,
//                contentDescription = "Şifre Gir",
//                tint = Color.White
//            )
//        }
//
//        // Kırmızı Ekrana Yayılma Animasyonu
//        AnimatedCircle(
//            isExpanded = isExpanded,
//            isReturning = isReturning,
//            onExpandEnd = {
//                isExpanded = false
//                navController.navigate("passwordScreen")
//            },
//            onCollapseEnd = {
//                isReturning = false
//            }
//        )
//    }
//}
//
//@Composable
//fun PasswordScreen(navController: NavController) {
//    var isReturning by remember { mutableStateOf(false) }
//
//    // Geri Butonu
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Red),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = "Şifre Gir Sayfası",
//            color = Color.White,
//            fontSize = 24.sp,
//            modifier = Modifier.clickable {
//                isReturning = true
//            }
//        )
//
//        // Geri dönüş animasyonu
//        AnimatedCircle(
//            isExpanded = isReturning,
//            isReturning = isReturning,
//            onExpandEnd = {
//                isReturning = false
//                navController.popBackStack()
//            },
//            onCollapseEnd = {}
//        )
//    }
//}
//
//@Composable
//fun BoxScope.AnimatedCircle(
//    isExpanded: Boolean,
//    isReturning: Boolean,
//    onExpandEnd: () -> Unit,
//    onCollapseEnd: () -> Unit
//) {
//    val transition = updateTransition(targetState = isExpanded, label = "circleTransition")
//
//    val size by transition.animateFloat(
//        label = "circleSize",
//        transitionSpec = {
//            if (isReturning) tween(durationMillis = 800) else tween(durationMillis = 800)
//        }
//    ) { expanded ->
//        if (expanded) 4000f else 56f
//    }
//
//    val alpha by transition.animateFloat(
//        label = "circleAlpha",
//        transitionSpec = { tween(durationMillis = 800) }
//    ) { expanded ->
//        if (expanded) 1f else 0f
//    }
//
//    LaunchedEffect(transition.currentState) {
//        if (!isExpanded && !isReturning) {
//            if (isReturning) onCollapseEnd() else onExpandEnd()
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .size(size.dp)
//            .alpha(alpha)
//            .background(Color.Red, shape = CircleShape)
//            .align(Alignment.BottomEnd)
//            .padding(16.dp)
//    )
//}

