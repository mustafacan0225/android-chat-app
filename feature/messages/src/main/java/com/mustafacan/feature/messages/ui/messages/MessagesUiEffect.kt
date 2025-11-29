package com.mustafacan.feature.messages.ui.messages

import com.mustafacan.core.ui.model.UserUiModel

sealed class MessagesUiEffect {
    data class NavigateToDirectMessage(val user: UserUiModel): MessagesUiEffect()
}
