package jp.co.yumemi.android.code_check.compose.ui

import android.content.res.Configuration
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun Theme(content: @Composable () -> Unit) {
    val isDarkMode = LocalContext.current.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    val colorScheme = if (isDarkMode) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            onPrimary = Color.Black,
            surface = Color(0xFF121212),
            onSurface = Color(0xFFE0E0E0),
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            onPrimary = Color.White,
            surface = Color.White,
            onSurface = Color.Black,
        )
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
