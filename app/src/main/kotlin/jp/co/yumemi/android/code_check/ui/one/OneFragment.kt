/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.one

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.model.Item

class OneFragment: Fragment(R.layout.fragment_one){

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val layoutManager = LinearLayoutManager(context)
        val binding = FragmentOneBinding.bind(view)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        val viewModel = OneViewModel()
        val adapter = CustomAdapter(
            object : CustomAdapter.OnItemClickListener {
                override fun itemClick(item: Item) {
                    viewModel.goToRepositoryFragment(item, findNavController())
                }
            }
        )

        viewModel.bindingHandler(context, viewModel, adapter, lifecycleScope, binding, layoutManager, dividerItemDecoration)

    }

}

