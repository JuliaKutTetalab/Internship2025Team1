package com.arathort.growbox.presentation.deviceconnection.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arathort.growbox.domain.models.library.CropType
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CropWheelPicker(
    items: List<CropType>,
    initialIndex: Int = 2,
    onSelectionChanged: (CropType) -> Unit
) {
    val itemHeight = 40.dp
    val visibleItemsCount = 5

    val pickerHeight = itemHeight * visibleItemsCount

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val firstVisibleIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val firstVisibleOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    val density = LocalDensity.current
    val itemHeightPx = with(density) { itemHeight.toPx() }

    LaunchedEffect(firstVisibleIndex, firstVisibleOffset) {
        val centerOffset = firstVisibleOffset + (itemHeightPx / 2)
        val indexOffset = (centerOffset / itemHeightPx).toInt()
        val centerIndex = (firstVisibleIndex + indexOffset).coerceIn(0, items.lastIndex)
        onSelectionChanged(items[centerIndex])
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(pickerHeight),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(itemHeight)
                .background(
                    color = Green800.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(Dimensions.mediumRadius)
                )
        )

        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = (pickerHeight - itemHeight) / 2),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(items) { index, item ->
                val opacity by remember {
                    derivedStateOf {
                        val currentItemInfo = listState.layoutInfo.visibleItemsInfo
                            .find { it.index - 1 == index }

                        if (currentItemInfo != null) {
                            val viewportCenter = listState.layoutInfo.viewportEndOffset / 2
                            val itemCenter = currentItemInfo.offset + (currentItemInfo.size / 2)
                            val distance = kotlin.math.abs(viewportCenter - itemCenter)

                            val maxDistance = itemHeightPx * (visibleItemsCount / 2)
                            (1f - (distance / maxDistance).coerceIn(0f, 1f)) * 0.7f + 0.3f
                        } else {
                            0.3f
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = opacity
                            scaleX = 0.8f + (opacity * 0.2f)
                            scaleY = 0.8f + (opacity * 0.2f)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.name,
                        style = Typography.headlineSmall,
                        color = Green800,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}