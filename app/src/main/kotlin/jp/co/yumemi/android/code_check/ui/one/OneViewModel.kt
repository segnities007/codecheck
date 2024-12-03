/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.one

import android.app.Application
import android.view.inputmethod.EditorInfo
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
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

/**
 * TwoFragment で使う
 */
class OneViewModel(
    val context: Application,
    val binding: FragmentOneBinding,
    val layoutManager: LinearLayoutManager,
) : ViewModel() {

    private val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)

    suspend fun searchResults(inputText: String): List<Item> {
        val client = HttpClient(Android)
        val items = mutableListOf<Item>()

        val url = "https://api.github.com/search/repositories"
        val contentType = "application/vnd.github.v3+json"

        val response = client.get<HttpResponse>(url) {
            header("Accept", contentType)
            parameter("q", inputText)
        }

        val jsonBody = JSONObject(response.receive<String>())
        val jsonItems = jsonBody.optJSONArray("items")!!

        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.optJSONObject(i)
            addItem(
                jsonItem.optString("full_name"),
                jsonItem.optJSONObject("owner")!!.optString("avatar_url"),
                jsonItem.optString("language"),
                jsonItem.optLong("stargazers_count"),
                jsonItem.optLong("watchers_count"),
                jsonItem.optLong("forks_conut"),
                jsonItem.optLong("open_issues_count"),
                items
            )
        }

        lastSearchDate = Date()

        return items.toList()
    }

    fun addItem(
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

    fun goToRepositoryFragment(
        item: Item,
        findNavController: NavController,
    ) {
        val action = OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item)
        findNavController.navigate(action)
    }

    fun bindingHandler(
        viewModel: OneViewModel,
        adapter: CustomAdapter,
        lifecycleScope: LifecycleCoroutineScope,
    ){
        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                val query = editText.text.toString()
                performSearch(query, viewModel, adapter, lifecycleScope)
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

    private fun performSearch(
        query: String,
        viewModel: OneViewModel,
        adapter: CustomAdapter,
        lifecycleScope: LifecycleCoroutineScope,
    ) {
        lifecycleScope.launch {
            val searchResult = viewModel.searchResults(query)
            adapter.submitList(searchResult)
        }
    }

}

