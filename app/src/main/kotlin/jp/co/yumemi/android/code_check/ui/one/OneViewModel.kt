/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.one

import android.content.Context
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.model.Item
import jp.co.yumemi.android.code_check.ui.one.adapter.CustomAdapter
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

/**
 * TwoFragment で使う
 */
class OneViewModel(
) : ViewModel() {

    fun bindingHandler(
        context: Context,
        viewModel: OneViewModel,
        adapter: CustomAdapter,
        lifecycleScope: LifecycleCoroutineScope,
        binding: FragmentOneBinding,
        layoutManager: LinearLayoutManager,
        dividerItemDecoration: DividerItemDecoration
    ){
        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                val query = editText.text.toString()
                viewModel.searchResults(context, query, viewModel, adapter, lifecycleScope)
                hideKeyboard(editText, context)
                editText.clearFocus()
                true
            } else {
                false
            }
        }

        binding.recyclerView.also{
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    private fun hideKeyboard(
        view: TextView,
        context: Context
    ) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }catch (e: Exception){
            Log.d(e.toString(), "imm is NullPointerException or other error")
        }
    }

    fun goToRepositoryFragment(
        item: Item,
        findNavController: NavController,
    ) {
        val action = OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item)
        findNavController.navigate(action)
    }

    private fun searchResults(
        context: Context,
        query: String,
        viewModel: OneViewModel,
        adapter: CustomAdapter,
        lifecycleScope: LifecycleCoroutineScope,
    ) {
        lifecycleScope.launch {
            val searchResult = viewModel.suspendSearchResults(context, query)
            adapter.submitList(searchResult)
        }
    }

    private suspend fun suspendSearchResults(
        context: Context,
        inputText: String,
    ): List<Item> {
        val client = HttpClient(Android)
        val items = mutableListOf<Item>()

        val url = "https://api.github.com/search/repositories"
        val contentType = "application/vnd.github.v3+json"

        val response = client.get<HttpResponse>(url) {
            header("Accept", contentType)
            parameter("q", inputText)
        }

        val jsonBody = JSONObject(response.receive<String>())
        val jsonItems = jsonBody.optJSONArray("items") ?: return items.toList()

        val defaultUrl = "https://yumemi.co.jp/grow-with-new-logo/img/common/logo_new.svg"

        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.optJSONObject(i)
            addItem(
                context,
                jsonItem.optString("full_name"),
                jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: defaultUrl,
                jsonItem.optString("language"),
                jsonItem.optLong("stargazers_count"),
                jsonItem.optLong("watchers_count"),
                jsonItem.optLong("forks_count"),
                jsonItem.optLong("open_issues_count"),
                items
            )
        }

        lastSearchDate = Date()

        return items.toList()
    }

    private fun addItem(
        context: Context,
        name: String,
        ownerIconUrl: String,
        language: String,
        stargazersCount: Long,
        watchersCount: Long,
        forksCount: Long,
        openIssuesCount: Long,
        items: MutableList<Item>
    ){
        items.add(
            Item(
                name = name,
                ownerIconUrl = ownerIconUrl,
                language = context.getString(R.string.written_language, language),
                stargazersCount = stargazersCount,
                watchersCount = watchersCount,
                forksCount = forksCount,
                openIssuesCount = openIssuesCount
            )
        )
    }

}

