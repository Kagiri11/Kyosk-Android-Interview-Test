package com.example.kyosktest.utils

sealed class Resource {
    object Loading : Resource()
    data class Success(val data: Any) : Resource()
    data class Error(val error: String) : Resource()
}
