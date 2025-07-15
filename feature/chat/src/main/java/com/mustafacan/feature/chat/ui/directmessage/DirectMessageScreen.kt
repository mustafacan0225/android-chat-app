package com.mustafacan.feature.chat.ui.directmessage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.theme.CardItemBackgroundColor
import com.mustafacan.core.ui.theme.MessagePageBackgroundColor
import com.mustafacan.core.ui.theme.MessagePageHeaderColor
import com.mustafacan.core.ui.theme.ProgressColor
import com.mustafacan.core.ui.theme.SeperatorColor
import com.mustafacan.core.ui.theme.TitleTextColor

@Composable
fun DirectMessageRoute(viewModel: DirectMessageViewModel, navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DirectMessageScreen(uiState, onEvent = { viewModel.sendEvent(it) })
}

@Composable
fun DirectMessageScreen(uiState: DirectMessageUiState, onEvent: (DirectMessageUiEvent) -> Unit) {

    if (uiState.initialProgressVisibility) {
        Column(modifier = Modifier.fillMaxSize().background(MessagePageBackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(color = ProgressColor)
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = if (isSystemInDarkTheme()) com.mustafacan.feature.chat.R.drawable.chat_dark_bg else com.mustafacan.feature.chat.R.drawable.chat_light_bg) ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Column(modifier = Modifier.fillMaxSize()) {
                uiState.receiverUser?.let {
                    DirectMessageHeader(uiState)
                    DirectMessageContent(uiState, onEvent)
                }

            }
        }
    }
}

@Composable
fun DirectMessageHeader(
    uiState: DirectMessageUiState
) {
    Column(modifier = Modifier.fillMaxWidth().background(MessagePageHeaderColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            text = uiState.receiverUser?.username?: "",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = TitleTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Card(modifier = Modifier.wrapContentWidth().padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = if (uiState.receiverUserStatus == stringResource(R.string.connection_state_online)) Color.Green else Color.Gray)) {
            Column() {
                Text(modifier = Modifier.wrapContentSize()
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    text = uiState.receiverUserStatus,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }

        }

        Spacer(modifier = Modifier.fillMaxWidth().padding(top = 8.dp).height(1.dp).background(SeperatorColor))
    }

}

@Composable
fun DirectMessageContent(uiState: DirectMessageUiState, onEvent: (DirectMessageUiEvent) -> Unit) {

}


