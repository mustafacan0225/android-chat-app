package com.mustafacan.core.ui.animation.transition

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object Transition {

    private const val defaultDuration = 500

    // 1. Slide in from the left (enters from left to right)
    fun enterFromLeft(duration: Int = defaultDuration) = slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(duration)
    ) + fadeIn(animationSpec = tween(duration))

    // 2. Slide out to the left (exits towards left)
    fun exitToLeft(duration: Int = defaultDuration) = slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(duration)
    ) + fadeOut(animationSpec = tween(duration))

    // 3. Slide in from the right (enters from right to left)
    fun enterFromRight(duration: Int = defaultDuration) = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(duration)
    ) + fadeIn(animationSpec = tween(duration))

    // 4. Slide out to the right (exits towards right)
    fun exitToRight(duration: Int = defaultDuration) = slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(duration)
    ) + fadeOut(animationSpec = tween(duration))
}
