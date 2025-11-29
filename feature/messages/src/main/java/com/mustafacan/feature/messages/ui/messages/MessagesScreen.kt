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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mustafacan.core.model.chat.UserRef
import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel
import com.mustafacan.core.model.users.User
import com.mustafacan.core.ui.component.error.ErrorView
import com.mustafacan.core.ui.component.header.ListHeaderItem
import com.mustafacan.core.ui.component.loading.VerticalRectangleShimmer
import com.mustafacan.core.ui.component.typing.TypingComponent
import com.mustafacan.core.ui.extension.formatAsLocalDateTime
import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.theme.BackgroundDark
import com.mustafacan.core.ui.theme.CardButtonTextColor
import com.mustafacan.core.ui.theme.CardItemBackgroundColor
import com.mustafacan.core.ui.theme.CardItemTextColor
import com.mustafacan.core.ui.theme.MessageCardUserNameTextColorForReceiver
import com.mustafacan.core.ui.theme.MessageItemDateBackground
import com.mustafacan.core.ui.theme.MessageItemUnreadBadgeColor
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
                    parentNavController.navigate(NavDestinationItem.DirectMessage(own = viewModel.getOwnInfo(), receiverUser = effect.user, NavDestinationItem.Users::class.qualifiedName?: "NavDestinationItem.Message"))
                }
            }

        }
    }

    MessagesScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) }, viewModel)
}

@Composable
fun MessagesScreen(uiState: MessagesUiState,
                   onEvent: (MessagesUiEvent) -> Unit,
                   viewModel: MessagesViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        ListHeaderItem(stringResource(R.string.messages_title))
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.loading) {
            VerticalRectangleShimmer()
        } else if (uiState.hasError) {
            ErrorView(message = stringResource(com.mustafacan.core.ui.R.string.default_error),
                onRetry = {
                    onEvent(MessagesUiEvent.Retry)
                })
        } else if (uiState.messageRooms.isEmpty()) {

        } else {
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
            /*LazyColumn(modifier = Modifier.weight(1f)) {
                items(uiState.messageRooms.size) { index ->
                    val message = uiState.messageRooms[index]
                    if (message != null) {
                        var userRef: UserRef? = null
                        var ownUser: UserRef? = null
                        message.users.forEach {
                            if (!it._id.equals(uiState.userId))
                                userRef = it
                            else
                                ownUser = it
                        }
                        MessageRoomItem(message, ownUser = ownUser, otherUser = userRef,
                            buttonClicked = {
                                onEvent(MessagesUiEvent.NavigateToDirectMessage(user = UserUiModel(id = userRef!!._id, username = userRef!!.username)))
                            }
                        )
                    }
                }
            }
        }*/
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
            verticalAlignment = Alignment.Top   // <<< en önemli satır
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

            Column(
                horizontalAlignment = Alignment.End
            ) {
                if (uiState.typingRoomIds.contains(messageRoom.id)) {
                    TypingComponent(Modifier.wrapContentWidth())
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
                                .background(
                                    color = MessageItemDateBackground,//MessageCardUserNameTextColorForReceiver.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 3.dp, vertical = 1.dp)
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        // Altındaki turkuaz küçük yuvarlak badge
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