/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.one

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.compose.ui.components.RepositoryList
import jp.co.yumemi.android.code_check.compose.ui.screens.search.Search
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.model.Item
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

/**
 * TwoFragment で使う
 */
class OneViewModel(
) : ViewModel() {

    fun goToRepositoryFragment(
        item: Item,
        findNavController: NavController,
    ) {
        val action = OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item)
        findNavController.navigate(action)
    }

}

