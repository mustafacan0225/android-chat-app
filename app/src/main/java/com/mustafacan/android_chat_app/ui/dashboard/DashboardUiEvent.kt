package com.mustafacan.android_chat_app.ui.dashboard

import com.mustafacan.core.ui.component.dialog.DialogModel

sealed class DashboardUiEvent {
    object ConnectSocket: DashboardUiEvent()
    object DisconnectSocket: DashboardUiEvent()
    data class ShowDialog(val dialogModel: DialogModel) : DashboardUiEvent()
    object DismissDialog : DashboardUiEvent()
    data class SetTopAppBarVisibility(val visible: Boolean) : DashboardUiEvent()
    data class SetBadgeVisibilityForMessagesTab(val visibility: Boolean) : DashboardUiEvent()
    data class SetBadgeVisibilityForRoomsTab(val visibility: Boolean) : DashboardUiEvent()
}