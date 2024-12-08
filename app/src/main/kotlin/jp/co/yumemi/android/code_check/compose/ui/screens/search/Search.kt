package jp.co.yumemi.android.code_check.compose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import jp.co.yumemi.android.code_check.compose.ui.components.input.InputForm
import jp.co.yumemi.android.code_check.compose.ui.components.RepositoryList
import jp.co.yumemi.android.code_check.model.Item

@Composable
fun Search(
    itemList: List<Item>,
    onItemClick: (Item) -> Unit
){
    Column(){
        InputForm()
        RepositoryList(itemList, onItemClick)
    }
}

