/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.app.Application
import android.content.Context
import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.util.*

/**
 * TwoFragment で使う
 */
class OneViewModel(
    val context: Application
) : ViewModel() {

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
            val name = jsonItem.optString("full_name")
            val ownerIconUrl = jsonItem.optJSONObject("owner")!!.optString("avatar_url")
            val language = jsonItem.optString("language")
            val stargazersCount = jsonItem.optLong("stargazers_count")
            val watchersCount = jsonItem.optLong("watchers_count")
            val forksCount = jsonItem.optLong("forks_conut")
            val openIssuesCount = jsonItem.optLong("open_issues_count")

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

        lastSearchDate = Date()

        return items.toList()
    }
}

