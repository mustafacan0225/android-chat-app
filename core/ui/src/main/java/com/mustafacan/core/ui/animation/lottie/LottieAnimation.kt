package com.mustafacan.core.ui.animation.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimation(animationResId: Int, modifier: Modifier) {

    val rawComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animationResId))
    val progress by animateLottieCompositionAsState(
        composition = rawComposition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = rawComposition,
        progress = { progress },
        modifier = modifier
    )
}