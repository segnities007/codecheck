package jp.co.yumemi.android.code_check.compose.ui.screens.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Date

class SearchViewModel: ViewModel(){

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    fun updateItems(newItems: List<Item>) {
        _items.value = newItems
    }

    fun sortItemsByName() {
        _items.value = _items.value.sortedBy { it.name }
    }

    fun sortItemsByNameDescending() {
        _items.value = _items.value.sortedByDescending { it.name }
    }

    //検索入力したらそれを表示する処理
    fun onEnterHandler(
        context: Context,
        input: String,
    ){
        searchResults(context, input)
    }

    private fun searchResults(
        context: Context,
        query: String,
    ) {
        viewModelScope.launch {
            //取得した情報を受け取る
            val searchResults: List<Item> = suspendSearchResults(context, query)
            updateItems(searchResults)
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