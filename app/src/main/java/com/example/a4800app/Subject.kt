package com.example.a4800app

import kotlinx.coroutines.CoroutineScope

interface Subject {
    fun attachObserver(observer : Observer)
    fun detachObserver(observer: Observer)
    fun notifyObserver(viewModelScope: CoroutineScope)
}