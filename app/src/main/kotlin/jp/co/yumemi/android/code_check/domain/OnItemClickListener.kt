package jp.co.yumemi.android.code_check.domain

import jp.co.yumemi.android.code_check.model.Item

interface OnItemClickListener{
    fun itemClick(item: Item)
}