package com.mustafacan.core.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserUiModel(val id: String, val username: String): Parcelable
