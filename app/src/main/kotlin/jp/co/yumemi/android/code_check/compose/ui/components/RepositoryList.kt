package jp.co.yumemi.android.code_check.compose.ui.components

import android.content.Context
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.co.yumemi.android.code_check.compose.ui.screens.search.SearchViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleCoroutineScope
import jp.co.yumemi.android.code_check.model.Item


//取得したRepositoriesをリスト表示する
@Composable
fun RepositoryList(
    searchViewModel: SearchViewModel = SearchViewModel(),
    onTapHandler: (Item) -> Unit,
){

    val items by searchViewModel.items.collectAsState()

    val fontSize = 16
    val horizontalPadding = 16
    val verticalPadding = 8
    val thickness = 0.4
    val textColor = colorScheme.onSurface

    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
    ){
        items(items){ item ->
            Box(modifier = Modifier.clickable{onTapHandler(item)},){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding.dp, vertical = verticalPadding.dp)
                ){
                    Text(text = item.name, fontSize = fontSize.sp, color = textColor)
                }
                HorizontalDivider(thickness = thickness.dp, color = Color.Gray)
            }
        }
    }
}