package com.mustafacan.core.common.app_event

sealed class AppEvent {
    object TokenExpired : AppEvent()
}