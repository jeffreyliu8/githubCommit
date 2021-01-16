package com.regiscorp.myapplication.other

data class Resource<out T>(val status: Status, val data: T? = null, val message: String? = null) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.ERROR, message = msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}