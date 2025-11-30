package com.mustafacan.feature.rooms.ui.rooms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.mustafacan.core.model.chat.UserRef
import com.mustafacan.core.ui.animation.lottie.LottieAnimation
import com.mustafacan.core.ui.component.error.ErrorView
import com.mustafacan.core.ui.component.header.ListHeaderItem
import com.mustafacan.core.ui.component.loading.VerticalRectangleShimmer
import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.theme.MessageItemUnreadBadgeColor
import com.mustafacan.core.ui.util.rememberFlowWithLifecycle
import com.mustafacan.feature.rooms.R
import com.mustafacan.feature.rooms.ui.rooms.model.GroupMessageRoomUiModel

@Composable
fun RoomsRoute(
    viewModel: RoomsViewModel,
    navController: NavHostController,
    parentNavController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = rememberFlowWithLifecycle(viewModel.uiEffect)
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when(effect) {
                is RoomsUiEffect.NavigateToDirectMessage -> {
                    parentNavController.navigate(NavDestinationItem.DirectMessage(own = viewModel.getOwnInfo(), receiverUser = effect.user, NavDestinationItem.ChatRooms::class.qualifiedName?: "NavDestinationItem.ChatRooms"))
                }

                RoomsUiEffect.ScrollToTop -> {
                    listState.animateScrollToItem(0)
                }
            }

        }
    }

    RoomsScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) }, listState)
}

@Composable
fun RoomsScreen(uiState: RoomsUiState,
                   onEvent: (RoomsUiEvent) -> Unit,
                    listState: LazyListState
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        ListHeaderItem(stringResource(R.string.rooms_title))
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.loading) {
            VerticalRectangleShimmer()
        } else if (uiState.hasError) {
            ErrorView(message = stringResource(com.mustafacan.core.ui.R.string.default_error),
                onRetry = {
                    onEvent(RoomsUiEvent.Retry)
                })
        } else if (uiState.messageRooms.isEmpty()) {
            // to do
        } else {
            LazyColumn(state = listState) {
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
                        RoomItem(
                            uiState= uiState,
                            messageRoom = room,
                            ownUser = ownUser,
                            buttonClicked = {
                                onEvent(RoomsUiEvent.SetHasNewMessage(room, false))
                                onEvent(RoomsUiEvent.NavigateToDirectMessage(user = UserUiModel(id = userRef!!._id, username = userRef!!.username)))
                                if (index == 0 && uiState.hasUnreadWhileTabClosed)
                                    onEvent(RoomsUiEvent.ClearHasUnreadWhileTabClosed)
                            }
                        )
                    }
                )
            }

        }

    }
}

@Composable
fun RoomItem(
    uiState: RoomsUiState,
    messageRoom: GroupMessageRoomUiModel,
    ownUser: UserRef?,
    buttonClicked: () -> Unit
) {
    val sender = if (messageRoom.lastMessage?.sender?._id?.equals(ownUser?._id) ?: false) {
        "${stringResource(com.mustafacan.core.ui.R.string.you)}:"
    } else {
        "(${messageRoom.lastMessage?.sender?.username ?: "?"}):"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable { buttonClicked() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
        ) {
            val imageUrl = messageRoom.roomImage ?: ""
            if (imageUrl.isNotBlank()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = messageRoom.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.12f))
                )
            }

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.05f),
                                Color.Black.copy(alpha = 0.55f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.45f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = messageRoom.name ?: "",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                maxLines = 1
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            val descriptionText = when {
                                !messageRoom.description.isNullOrBlank() -> messageRoom.description!!
                                messageRoom.lastMessage?.message != null -> "$sender ${messageRoom.lastMessage?.message}"
                                else -> ""
                            }

                            Text(
                                text = descriptionText,
                                style = TextStyle(
                                    color = Color.White.copy(alpha = 0.90f),
                                    fontSize = 13.sp
                                ),
                                maxLines = 1
                            )
                        }

                        if (uiState.typingRoomIds.contains(messageRoom.id)) {
                            LottieAnimation(com.mustafacan.core.ui.R.raw.typing, Modifier.width(50.dp).height(25.dp))
                        } else if (messageRoom.hasNewMessage) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        color = MessageItemUnreadBadgeColor,
                                        shape = CircleShape
                                    )
                            )
                        }

                    }
                }
            }

        }
    }
}
