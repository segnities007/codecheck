package jp.co.yumemi.android.code_check.compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.co.yumemi.android.code_check.model.Item

@Composable
fun RepositoryList(
    itemList: List<Item>,
    onItemClick: (Item) -> Unit,
){

    val fontSize = 12.0
    val horizontalPadding = 16.0
    val verticalPadding = 8.0
    val thickness = 0.4

    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
    ){
        items(itemList){ item ->
            Box(modifier = Modifier.clickable{onItemClick(item)},){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding.dp, vertical = verticalPadding.dp)
                ){
                    Text(text = item.name, fontSize = fontSize.sp)
                }
                HorizontalDivider(thickness = thickness.dp, color = Color.Gray)
            }
        }
    }
}