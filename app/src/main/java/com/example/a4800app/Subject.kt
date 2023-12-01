package com.example.a4800app

interface Subject {
    fun attachObserver(observer : Observer)
    fun detachObserver(observer: Observer)
    fun notifyObserver()
}