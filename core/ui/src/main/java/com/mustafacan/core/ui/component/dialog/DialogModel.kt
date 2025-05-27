package com.mustafacan.core.ui.component.dialog

data class DialogModel(
    val message: String,
    val dialogType: DialogType = DialogType.Info,
    val onDismiss: (() -> Unit)? = null,
    val onCancel: (() -> Unit)? = null,
    val onConfirm: (() -> Unit)? = null,
    val cancelText: String? = null,
    val confirmText: String? = null,
    val isCancelable: Boolean = true
)
