package com.tiooooo.core.network.data

import com.tiooooo.core.constant.Constants

fun <T> States<T>.handleStates(
    loadingBlock: ((Boolean) -> Unit)? = null,
    emptyBlock: (() -> Unit)? = null,
    errorBlock: ((String) -> Unit)? = null,
    successBlock: (T) -> Unit,
) {
    when (this) {
        is States.Loading -> {
            loadingBlock?.invoke(true)
        }

        is States.Success -> {
            loadingBlock?.invoke(false)
            successBlock.invoke(data)
        }

        is States.Empty -> {
            loadingBlock?.invoke(false)
            emptyBlock?.invoke()
        }

        is States.Error -> {
            loadingBlock?.invoke(false)
            val error = when (this) {
                is HttpError -> message
                is NoInternetError, is TimeOutError -> Constants.ERROR_CODE_NO_INTERNET
                else -> Constants.ERROR_DEFAULT
            }
            errorBlock?.invoke(error)
        }
    }
}
