package com.arathort.growbox.presentation.main

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.navigation.TabRoute
import com.arathort.growbox.ui.theme.Typography

@Composable
fun GrowBoxBottomBar(
    currentTab: TabRoute,
    onNavigate: (TabRoute) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Settings,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,


         modifier = Modifier.shadow(
            elevation = Dimensions.micro,
        )
    ) {
        items.forEach { item ->
            val isSelected = currentTab == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(Dimensions.standardIconSize)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = Typography.labelMedium.copy(
                            fontWeight = FontWeight.W800,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        ),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,

                    indicatorColor = Color.Transparent,

                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                    unselectedTextColor = MaterialTheme.colorScheme.outline,
                )
            )
        }
    }
}