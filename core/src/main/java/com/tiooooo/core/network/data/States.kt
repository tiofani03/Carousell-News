package com.tiooooo.core.network.data

sealed interface States<out T> {
    object Loading : States<Nothing>

    open class Error(
        open val message: String,
    ) : States<Nothing>

    object Empty : States<Nothing>

    class Success<T>(
        val data: T,
    ) : States<T>
}
