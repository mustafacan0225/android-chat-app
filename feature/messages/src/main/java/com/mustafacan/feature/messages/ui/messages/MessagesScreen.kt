package com.mustafacan.feature.messages.ui.messages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mustafacan.core.model.chat.UserRef
import com.mustafacan.core.ui.animation.lottie.LottieAnimation
import com.mustafacan.core.ui.component.error.ErrorView
import com.mustafacan.core.ui.component.header.ListHeaderItem
import com.mustafacan.core.ui.component.loading.VerticalRectangleShimmer
import com.mustafacan.core.ui.extension.formatAsLocalDateTime
import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.theme.CardItemBackgroundColor
import com.mustafacan.core.ui.theme.CardItemTextColor
import com.mustafacan.core.ui.theme.MessageItemUnreadBadgeColor
import com.mustafacan.core.ui.theme.TitleTextColor
import com.mustafacan.core.ui.util.rememberFlowWithLifecycle
import com.mustafacan.feature.messages.R
import com.mustafacan.feature.messages.ui.messages.model.DirectMessageRoomUiModel

@Composable
fun MessagesRoute(
    viewModel: MessagesViewModel,
    navController: NavHostController,
    parentNavController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = rememberFlowWithLifecycle(viewModel.uiEffect)

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when(effect) {
                is MessagesUiEffect.NavigateToDirectMessage -> {
                    parentNavController.navigate(NavDestinationItem.DirectMessage(own = viewModel.getOwnInfo(), receiverUser = effect.user, NavDestinationItem.Messages::class.qualifiedName?: "NavDestinationItem.Messages"))
                }
            }

        }
    }

    MessagesScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) })
}

@Composable
fun MessagesScreen(uiState: MessagesUiState,
                   onEvent: (MessagesUiEvent) -> Unit) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {

        if (uiState.loading) {
            ListHeaderItem(stringResource(R.string.messages_title))
            Spacer(modifier = Modifier.height(16.dp))
            VerticalRectangleShimmer()
        } else if (uiState.hasError) {
            ListHeaderItem(stringResource(R.string.messages_title))
            Spacer(modifier = Modifier.height(16.dp))
            ErrorView(message = stringResource(com.mustafacan.core.ui.R.string.default_error),
                onRetry = {
                    onEvent(MessagesUiEvent.Retry)
                })
        } else if (uiState.messageRooms.isEmpty()) {
            EmptyMessageScreen()
        } else {
            ListHeaderItem(stringResource(R.string.messages_title))
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(
                    count = uiState.messageRooms.size,
                    key = { index -> uiState.messageRooms[index].id },
                    itemContent = { index ->
                        val room = uiState.messageRooms[index]
                        var userRef: UserRef? = null
                        var ownUser: UserRef? = null
                        room.users.forEach {
                            if (!it._id.equals(uiState.userId))
                                userRef = it
                            else
                                ownUser = it
                        }
                        MessageRoomItem(
                            uiState= uiState,
                            messageRoom = room,
                            ownUser = ownUser,
                            otherUser = userRef,
                            buttonClicked = {
                                onEvent(MessagesUiEvent.SetHasNewMessage(room, false))
                                onEvent(MessagesUiEvent.NavigateToDirectMessage(user = UserUiModel(id = userRef!!._id, username = userRef!!.username)))
                                if (index == 0 && uiState.hasUnreadWhileTabClosed)
                                    onEvent(MessagesUiEvent.ClearHasUnreadWhileTabClosed)
                            }
                        )
                    }
                )
            }

        }

    }
}

@Composable
fun MessageRoomItem(
    uiState: MessagesUiState,
    messageRoom: DirectMessageRoomUiModel,
    ownUser: UserRef?,
    otherUser: UserRef?,
    buttonClicked: () -> Unit
) {
    val sender = if (messageRoom.lastMessage?.sender?._id?.equals(ownUser?._id)?: false) "${stringResource(
        com.mustafacan.core.ui.R.string.you)}:" else "(${otherUser?.username}):"
    Card(
        modifier = Modifier.clickable { buttonClicked() }
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = CardItemBackgroundColor)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_message),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.wrapContentSize()
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = otherUser?.username ?: "",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    color = CardItemTextColor
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "$sender ${messageRoom.lastMessage?.message}",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    color = CardItemTextColor
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(horizontalAlignment = Alignment.End,) {
                if (uiState.typingRoomIds.contains(messageRoom.id)) {
                    Box(
                        modifier = Modifier.wrapContentHeight(),
                        contentAlignment = Alignment.Center
                    ) {

                        LottieAnimation(com.mustafacan.core.ui.R.raw.typing, Modifier.width(50.dp).height(25.dp))

                    }
                } else {
                    if(messageRoom.hasNewMessage) {
                        Text(
                            text = messageRoom.lastMessage?.createdAt?.formatAsLocalDateTime() ?: "",
                            style = TextStyle(
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = CardItemTextColor,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(6.dp)
                                .padding(horizontal = 3.dp, vertical = 1.dp)
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .size(12.dp)
                                .background(
                                    color = MessageItemUnreadBadgeColor,
                                    shape = CircleShape
                                )
                        )
                    } else {
                        Text(modifier = Modifier.padding(6.dp),
                            text = messageRoom.lastMessage?.createdAt?.formatAsLocalDateTime() ?: "",
                            style = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Medium),
                            color = CardItemTextColor,
                            maxLines = 1
                        )
                    }

                }



            }
        }
    }
}

@Composable
fun EmptyMessageScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(WindowInsets.navigationBars.asPaddingValues())
            .imePadding()){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.messages_empty_title),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    color = TitleTextColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                LottieAnimation(
                    com.mustafacan.core.ui.R.raw.empty_message_anim,
                    modifier = Modifier
                        .width(250.dp)
                        .height(250.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.messages_empty_description),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    color = TitleTextColor,
                    textAlign = TextAlign.Center
                )
            }

        }

    }

}