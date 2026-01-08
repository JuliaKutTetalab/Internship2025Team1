package com.arathort.growbox.domain.models.user

data class HarvestHistoryItem(

    val id: String,

    val cropName: String,

    val cropImageUrl: String,

    val harvestDate: Long,

    val daysGrown: Int,

    val yieldAmount: Double,

    val harvestCount: Int

)