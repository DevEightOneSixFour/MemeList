package com.example.memelist.model

// group of related objects
sealed class UIState {
    object Loading: UIState()
    class Error(val error: Exception): UIState()
    class Success<T>(val response: T): UIState()
}

// group of related constants
enum class StateUI {
    LOADING, // 0
    ERROR, // 1
    SUCCESS // 2
}
