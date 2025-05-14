package com.mustafacan.core.common.app_event

import com.mustafacan.core.common.model.PopupType

sealed class AppEvent {
    object TokenExpired : AppEvent()
    data class ShowPopup(
        val message: String,
        val popupType: PopupType = PopupType.Info,
        val onDismiss: (() -> Unit)? = null,
        val onCancel: (() -> Unit)? = null,
        val onConfirm: (() -> Unit)? = null,
        val cancelText: String? = null,
        val comfirmText: String? = null
    ) : AppEvent()
}