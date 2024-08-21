package com.example.mylistproject.domain.model

/**
 * Represents an item in a list.
 */
data class Item(
    val uuid: String?,
    val full_name: String?,
    val email_address: String?,
    val phone_number: String?,
    val biography: String?,
    val photo_url_small: String?,
    val photo_url_large: String?,
    val team: String?,
    val employee_type: String?,
) {
    fun isMalformed(): Boolean {
        return uuid.isNullOrBlank() || full_name.isNullOrBlank() || email_address.isNullOrBlank()
    }
}

data class ApiResponse(
    val employees: ArrayList<Item>
)
