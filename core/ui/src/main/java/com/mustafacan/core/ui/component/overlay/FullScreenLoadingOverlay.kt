package com.mustafacan.core.ui.component.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.animation.lottie.LottieAnimation
import com.mustafacan.core.ui.theme.PrimaryDark

@Composable
fun FullScreenLoadingOverlay(
    message: String,
    animationResId: Int = R.raw.lottie_splash,
    animationOrIconModifier: Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .zIndex(1f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(animationResId, modifier = animationOrIconModifier)
            Text(text = message, color = PrimaryDark)
        }
    }
}