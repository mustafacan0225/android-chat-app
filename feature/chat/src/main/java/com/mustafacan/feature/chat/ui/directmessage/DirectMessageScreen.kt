package com.mustafacan.feature.chat.ui.directmessage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mustafacan.core.model.chat.Message
import com.mustafacan.core.ui.R
import com.mustafacan.feature.chat.R as chatR

import com.mustafacan.core.ui.animation.lottie.LottieAnimation
import com.mustafacan.core.ui.component.error.ErrorView
import com.mustafacan.core.ui.component.loading.MoreItemsLoading
import com.mustafacan.core.ui.component.loading.VerticalRectangleShimmer
import com.mustafacan.core.ui.component.textfield.MessageTextFieldColors
import com.mustafacan.core.ui.component.typing.TypingComponent
import com.mustafacan.core.ui.extension.formatAsLocalDateTime
import com.mustafacan.core.ui.theme.MessageCardBackgroundColorForReceiver
import com.mustafacan.core.ui.theme.MessageCardBackgroundColorForSender
import com.mustafacan.core.ui.theme.MessageCardDateColorForReceiver
import com.mustafacan.core.ui.theme.MessageCardDateColorForSender
import com.mustafacan.core.ui.theme.MessageCardTextColorForReceiver
import com.mustafacan.core.ui.theme.MessageCardTextColorForSender
import com.mustafacan.core.ui.theme.MessageCardUserNameTextColorForReceiver
import com.mustafacan.core.ui.theme.MessagePageBackgroundColor
import com.mustafacan.core.ui.theme.MessagePageHeaderColor
import com.mustafacan.core.ui.theme.MessagePageHeaderTitleTextColor
import com.mustafacan.core.ui.theme.ProgressColor
import com.mustafacan.core.ui.theme.SeperatorColor
import com.mustafacan.core.ui.theme.TitleTextColor
import com.mustafacan.core.ui.util.rememberFlowWithLifecycle
import kotlinx.coroutines.delay

@Composable
fun DirectMessageRoute(viewModel: DirectMessageViewModel, navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = rememberFlowWithLifecycle(viewModel.uiEffect)
    val pagedMessages : LazyPagingItems<Message> = viewModel.messagesPagingDataFlow.collectAsLazyPagingItems()
    val messagesLazyListState = rememberLazyListState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(messagesLazyListState.firstVisibleItemIndex, messagesLazyListState.firstVisibleItemScrollOffset) {
        viewModel.updateScrollPosition(
            index = messagesLazyListState.firstVisibleItemIndex,
            offset = messagesLazyListState.firstVisibleItemScrollOffset
        )
    }

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when(effect) {
                DirectMessageUiEffect.ScrollToBottom -> {
                    val lastIndex = pagedMessages.itemCount + uiState.socketMessages.size - 1
                    if (lastIndex >= 0) {
                        delay(50)
                        messagesLazyListState.scrollToItem(lastIndex)
                    }
                }

                DirectMessageUiEffect.ScrollToItem -> {
                    messagesLazyListState.scrollToItem(
                        index = uiState.previousFirstVisibleItemIndex + 5, // pageSize
                        scrollOffset = uiState.previousFirstVisibleItemOffset
                    )

                }

                DirectMessageUiEffect.HideKeyboard -> {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            }
        }
    }

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    Log.d("LifecycleObserver", "ON_START DM-> ConnectSocket event sent")
                }

                Lifecycle.Event.ON_RESUME -> {
                    Log.d("LifecycleObserver", "ON_RESUME DM->")

                }

                Lifecycle.Event.ON_STOP -> {
                    Log.d("LifecycleObserver", "ON_STOP DM-> DisconnectSocket event sent")
                    if (uiState.messageValue.length > 0)
                        viewModel.stopTyping()
                }

                else -> {
                    Log.d("LifecycleObserver", "DM Unhandled lifecycle event: $event")
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            Log.d("LifecycleObserver", "Observer removed DM-> DisconnectSocket event sent")

        }
    }

    DirectMessageScreen(
        uiState = uiState,
        pagedMessages = pagedMessages,
        messagesLazyListState = messagesLazyListState,
        onEvent = { viewModel.sendEvent(it) }
    )
}

@Composable
fun DirectMessageScreen(uiState: DirectMessageUiState, pagedMessages: LazyPagingItems<Message>, messagesLazyListState: LazyListState, onEvent: (DirectMessageUiEvent) -> Unit) {

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

            Column(modifier = Modifier.fillMaxSize().padding(WindowInsets.navigationBars.asPaddingValues())
                .imePadding()) {
                uiState.receiverUser?.let {
                    DirectMessageHeader(uiState)
                    MoreItemsLoading(uiState.isPrependingMessages)
                    DirectMessageContent(uiState, pagedMessages, messagesLazyListState, onEvent, modifier = Modifier.weight(1f).fillMaxWidth())



                    OutlinedTextField(
                        value = uiState.messageValue,
                        onValueChange = { onEvent(DirectMessageUiEvent.MessageValueChanged(it)) },
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        trailingIcon = {
                            val image = Icons.Filled.Send

                            IconButton(onClick = { onEvent(DirectMessageUiEvent.SendMessage) }) {
                                Icon(imageVector = image, contentDescription = "send", tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { onEvent(DirectMessageUiEvent.SendMessage) }
                        ),
                        shape = RoundedCornerShape(24.dp),
                        colors = MessageTextFieldColors,
                        placeholder = {
                            Text(text = stringResource(id = chatR.string.message_placeholder))
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun DirectMessageHeader(
    uiState: DirectMessageUiState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MessagePageHeaderColor)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = uiState.receiverUser?.username ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MessagePageHeaderTitleTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(4.dp))

            Card(
                modifier = Modifier.wrapContentWidth().padding(start = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (uiState.receiverUserStatus == stringResource(R.string.connection_state_online))
                        Color(0xFF4CAF50)
                    else
                        Color.Gray
                )
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                        text = uiState.receiverUserStatus,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                        color = Color.White
                    )
                }
            }
        }

        if (uiState.showTyping) {
            TypingComponent(Modifier
                .padding(start = 8.dp)
                .wrapContentWidth())
        }

        Spacer(
            modifier = Modifier.padding(top = 8.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(SeperatorColor)
        )
    }
}


@Composable
fun DirectMessageContent(
    uiState: DirectMessageUiState,
    pagedMessages: LazyPagingItems<Message>,
    messagesLazyListState: LazyListState,
    onEvent: (DirectMessageUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(pagedMessages.loadState) {
        onEvent(DirectMessageUiEvent.MessagesLoadStateChanged(pagedMessages))
    }

    if (uiState.isLoadingMessages) {
        Log.d("dmcompose", "isLoadingMessages")
        VerticalRectangleShimmer()
    }

    else if (uiState.messagesLoadingError != null) {

        ErrorView(
            message = stringResource(R.string.default_error),
            onRetry = {
                // retry
            }
        )
    }

    else if (uiState.isMessageListEmpty) {
        EmptyMessageScreen(uiState, onEvent)
    }

    else {
        LazyColumn(
            modifier = modifier.fillMaxWidth().padding(top = 8.dp, bottom = 16.dp),
            state = messagesLazyListState
        ) {

            //pagination messages
            items(pagedMessages.itemCount) { index ->
                val message = pagedMessages[index]
                message?.let {
                    MessageItem(uiState, message = it)
                }
            }

            //socket messages
            items(uiState.socketMessages.size) { index ->
                val message = uiState.socketMessages[index]
                message?.let {
                    MessageItem(uiState, message = it)
                }
            }

        }



    }

    if (uiState.messagesPrependError != null) {
        ErrorView(
            message = stringResource(R.string.default_error),
            onRetry = {
                // retry
            }
        )
    }
}

@Composable
fun EmptyMessageScreen(uiState: DirectMessageUiState, onEvent: (DirectMessageUiEvent) -> Unit) {
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
                    text = stringResource(com.mustafacan.feature.chat.R.string.first_message_title),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    color = TitleTextColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                LottieAnimation(
                    R.raw.empty_message_anim,
                    modifier = Modifier
                        .width(250.dp)
                        .height(250.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(com.mustafacan.feature.chat.R.string.first_message),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    color = TitleTextColor
                )
            }

            OutlinedTextField(
                value = uiState.messageValue,
                onValueChange = { onEvent(DirectMessageUiEvent.MessageValueChanged(it)) },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                trailingIcon = {
                    val image = Icons.Filled.Send

                    IconButton(onClick = { onEvent(DirectMessageUiEvent.SendMessage) }) {
                        Icon(imageVector = image, contentDescription = "send", tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { onEvent(DirectMessageUiEvent.SendMessage) }
                ),
                shape = RoundedCornerShape(24.dp),
                colors = MessageTextFieldColors,
                placeholder = {
                    Text(text = stringResource(id = chatR.string.message_placeholder))
                }
            )
        }

    }

}

@Composable
fun MessageItem(uiState: DirectMessageUiState, message: Message) {
    val isOwnMessage = message.sender._id.equals(uiState.userId)

    val backgroundColor = if (isOwnMessage) MessageCardBackgroundColorForSender else MessageCardBackgroundColorForReceiver
    val horizontalPadding = if (isOwnMessage) PaddingValues(top = 8.dp, bottom = 8.dp, start = 100.dp, end = 16.dp)
    else PaddingValues(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 100.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontalPadding),
        horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            if (!isOwnMessage) {
                Text(
                    text = message.sender.username,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = MessageCardUserNameTextColorForReceiver
                )
            }

            Text(
                text = message.message,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isOwnMessage) MessageCardTextColorForSender else MessageCardTextColorForReceiver
            )
            Text(
                text = message.createdAt.formatAsLocalDateTime(),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = if (isOwnMessage) MessageCardDateColorForSender else MessageCardDateColorForReceiver
            )
        }
    }
}



