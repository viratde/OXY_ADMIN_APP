package com.oxyhotels.admin.feature_manager.domain.models

data class Manager(
    val _id: String = "",
    val username: String = "",
    val password: String = "",
    val name: String = "",
    val dob: String = "",
    val phoneNumber: String = "",
    val phoneNumbers: List<String> = listOf(),
    val email: String = "",
    val emails: List<String> = listOf(),
    val aadhar: String = "",
    val pan: String = "",
    val gstNo: String = "",
    val businessName: String = "",
    val address: String = "",
    val did: String = "",
    val permissions: List<Permission> = listOf(),
    val department: ManagerDepartment? = null
)
