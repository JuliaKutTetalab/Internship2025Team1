package com.arathort.growbox.presentation.deviceconnection.search

data class SearchingScreenUiState(
    val mockGrowBox: MockGrowBox = MockGrowBox(),
    val isFound: Boolean = false,
)

data class MockGrowBox(
    val name: String = "Growbox",
    val model: String = "Fantastic Gin-10",
    val version: String = "1.1.5",
)
