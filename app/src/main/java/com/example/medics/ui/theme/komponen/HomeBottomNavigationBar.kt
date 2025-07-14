package com.example.medics.ui.theme.komponen

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.medics.ui.screens.BottomNavItem

@Composable
fun HomeBottomNavigationBar(
    items: List<BottomNavItem>,
    selectedRoute: String,
    onItemClick: (route: String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route,
                onClick = { onItemClick(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF008080),
                    selectedTextColor = Color(0xFF008080),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFF008080).copy(alpha = 0.1f)
                )
            )
        }
    }
}