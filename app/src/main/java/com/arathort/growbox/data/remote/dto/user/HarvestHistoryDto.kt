package com.arathort.growbox.data.remote.dto.user

data class HarvestHistoryDto(

    val id: String = "",

    val crop_name: String = "",

    val crop_image_url: String = "",

    val harvest_date: Long = 0,

    val days_grown: Int = 0,

    val yield_amount: Double = 0.0,

    val harvest_count: Int = 1

)
