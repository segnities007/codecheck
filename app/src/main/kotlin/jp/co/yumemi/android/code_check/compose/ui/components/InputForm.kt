package jp.co.yumemi.android.code_check.compose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.ktor.http.cio.websocket.Frame.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun InputForm(){
    val elevation = 8.0
    val padding = 12.0
//    val radius = 12.0
    val height = padding*2 + 12
    var value by remember { mutableStateOf("") }

    ElevatedCard(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(height.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation.dp
        ),
    ){
        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Enter text") },
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp),
        )
    }
}