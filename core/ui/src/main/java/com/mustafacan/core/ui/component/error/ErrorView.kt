package com.mustafacan.core.ui.component.error

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.animation.lottie.LottieAnimation

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Row (
        modifier = Modifier.clickable { onRetry() }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LottieAnimation(R.raw.failed, Modifier.width(100.dp).height(80.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

    }
}