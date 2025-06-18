package com.mustafacan.core.ui.component.notfound

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.animation.lottie.LottieAnimation
import com.mustafacan.core.ui.theme.CardItemTextColor

@Composable
fun NotFoundScreenForSearch() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(R.raw.search, modifier = Modifier.size(150.dp))

        Text(
            text = stringResource(id = R.string.not_found),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = CardItemTextColor
        )
    }
}