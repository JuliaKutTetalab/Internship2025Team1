package com.arathort.growbox.data.remote.dto.user

data class UserProfileDto(

    val uid: String = "",

    val email: String = "",

    val display_name: String = "",

    val photo_url: String? = null,

    val total_harvests_count: Int = 0,

    val total_days_active: Int = 0,

    val current_crop_type: String = "",

)