package ru.loyalman.android.tapmobileyoutube.ui.main

import ru.loyalman.android.base.BaseEvent

sealed class MainEvent : BaseEvent {
    data class Error(val t: Throwable) : MainEvent()
}