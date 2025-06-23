package com.mustafacan.core.appevent

sealed class AppEvent {
    object TokenExpired : AppEvent()
}