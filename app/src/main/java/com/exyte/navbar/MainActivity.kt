package com.exyte.navbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.StraightIndent
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.navbar.colorButtons.ColorButton
import com.exyte.navbar.ui.theme.ElectricViolet
import com.exyte.navbar.ui.theme.LimeAccent
import com.exyte.navbar.ui.theme.RoyalPurple
import com.exyte.navbar.ui.theme.amberAccent
import com.exyte.navbar.ui.theme.cyanAccent
import com.exyte.navbar.ui.theme.deepOrangeAccent
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController: SystemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.isStatusBarVisible = false
                systemUiController.isNavigationBarVisible = false
            }

            // State variables
            var screenColor by remember { mutableStateOf(RoyalPurple) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(screenColor) // use state variable here
            ) {
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    ColorButtonNavBar{ selectedIndex ->
                        screenColor = when (selectedIndex) {
                            0 -> ElectricViolet
                            1 -> deepOrangeAccent
                            2 -> LimeAccent
                            3 -> amberAccent
                            4 -> cyanAccent
                            else -> RoyalPurple
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorButtonNavBar(onTabSelected: (Int) -> Unit) {
    var selectedItem by remember { mutableStateOf(0) }
    var prevSelectedIndex by remember { mutableStateOf(0) }


    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 0.dp)
            .height(110.dp),
        selectedIndex = selectedItem,
        ballColor = Color.White,
        cornerRadius = shapeCornerRadius(25.dp),
        ballAnimation = Straight(
            spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessVeryLow)
        ),
        indentAnimation = StraightIndent(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(1000)
        )
    ) {
        colorButtons.forEachIndexed { index, it ->
            ColorButton(
                modifier = Modifier.fillMaxSize(),
                prevSelectedIndex = prevSelectedIndex,
                selectedIndex = selectedItem,
                index = index,
                onClick = {
                    prevSelectedIndex = selectedItem
                    selectedItem = index
                    onTabSelected(index)
                },
                icon = it.icon,
                contentDescription = stringResource(id = it.description),
                animationType = it.animationType,
                background = it.animationType.background
            )
        }
    }
}

const val Duration = 500
const val DoubleDuration = 1000