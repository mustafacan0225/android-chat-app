package com.mustafacan.feature.chat.ui.directmessage

sealed class DirectMessageUiEffect {
    object ScrollToBottom: DirectMessageUiEffect()
    object ScrollToItem: DirectMessageUiEffect()
}
