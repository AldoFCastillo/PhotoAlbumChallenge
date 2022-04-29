package com.example.apkprueba.services

sealed class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {

    enum class Status {
        STARTED,
        LOADING,
        SUCCESS,
        ERROR,
        END
    }

    class Success<T>(data: T, status: Status = Status.SUCCESS) : Resource<T>(status, data)
    class Loading<T>(data: T? = null, status: Status = Status.LOADING) : Resource<T>(status, data)
    class Started<T>(data: T? = null, status: Status = Status.STARTED) : Resource<T>(status, data)
    class Error<T>(message: String, data: T? = null, status: Status = Status.ERROR) :
        Resource<T>(status, data, message)

    class End<T>(message: String?, data: T? = null, status: Status = Status.END) :
        Resource<T>(status, data, message)
}
