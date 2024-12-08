package jp.co.yumemi.android.code_check.compose.ui.screens.search

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import jp.co.yumemi.android.code_check.compose.ui.components.input.InputForm
import jp.co.yumemi.android.code_check.compose.ui.components.RepositoryList
import jp.co.yumemi.android.code_check.model.Item


@Composable
fun Search(
    view: View,
    context: Context,
    searchViewModel: SearchViewModel = SearchViewModel(),
    onTapHandler: (Item) -> Unit
){
    Column(){
        InputForm(context, searchViewModel)
        RepositoryList(searchViewModel, onTapHandler)
    }
}

