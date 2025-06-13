package com.mustafacan.core.ui.component.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun MoreItemsLoading(isLoading: Boolean) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            visible = true
        } else {
            delay(1000)
            visible = false

        }
    }

    if(visible){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

}