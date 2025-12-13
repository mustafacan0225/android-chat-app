package com.mustafacan.core.ui.component.typing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.animation.lottie.LottieAnimation
import com.mustafacan.core.ui.theme.TitleTextColor
import com.mustafacan.core.ui.theme.TypingComponentTitleTextColor

@Composable
fun TypingComponent(modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        LottieAnimation(
            R.raw.lottie_splash,
            Modifier
                .width(50.dp)
                .height(35.dp)
        )
        Text(
            text = stringResource(R.string.typing),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color.White
        )
    }
}