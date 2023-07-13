package com.example.findword.presentation

enum class STATE_TYPE {
    INIT,
    LOADING,
    ERROR,
    SUCCESS
}

sealed class StateResource<DATA>(
    val data: DATA? = null,
    val message: String? = null,
    val stateType: STATE_TYPE? = null,
    val throwable: Throwable? = null
) {
    class Init<DATA>: StateResource<DATA>(stateType = STATE_TYPE.INIT)

    class Loading<DATA> : StateResource<DATA>(stateType = STATE_TYPE.LOADING)

    class Error<DATA>(throwable: Throwable?) : StateResource<DATA>(throwable = throwable, stateType = STATE_TYPE.ERROR)

    class Success<DATA>(data: DATA): StateResource<DATA>(data = data, stateType = STATE_TYPE.SUCCESS)
}
