package com.example.myandroiddevhw12.ui.main

sealed class State {

    object NoSearch : State()
    object Loading : State()
    data class Success(val resultSearch: String) : State()

}