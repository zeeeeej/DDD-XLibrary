package com.zeeeeej.xlibrary.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface MVIState
interface MVIIntent

interface MVIDispatcher<in Intent : MVIIntent> {
    fun dispatch(intent: Intent)
}

abstract class MVIViewModel<State : MVIState, in Intent : MVIIntent>(
    init: State,
) : ViewModel(), MVIDispatcher<Intent> {
    private val _state: MutableStateFlow<State> = MutableStateFlow(init)
    val state: StateFlow<State> = _state.asStateFlow()

    private val intentChannel: Channel<Intent> = Channel()

    init {
        viewModelScope.launch {
            intentChannel.receiveAsFlow().collect { curIntent ->
                _state.handle(intent = curIntent)
            }
        }
    }

    override fun dispatch(intent: Intent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    abstract fun MutableStateFlow<State>.handle(intent: Intent)
}