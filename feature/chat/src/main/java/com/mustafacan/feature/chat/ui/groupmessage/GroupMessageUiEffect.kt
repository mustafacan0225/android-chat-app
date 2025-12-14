package com.mustafacan.feature.chat.ui.groupmessage

sealed class GroupMessageUiEffect() {
     object ScrollToBottom: GroupMessageUiEffect()
     object ScrollToItem: GroupMessageUiEffect()
     object HideKeyboard: GroupMessageUiEffect()
 }
