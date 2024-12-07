/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.one

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.domain.OnItemClickListener
import jp.co.yumemi.android.code_check.model.Item
import jp.co.yumemi.android.code_check.ui.one.adapter.CustomAdapter

class OneFragment: Fragment(R.layout.fragment_one){

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val layoutManager = LinearLayoutManager(context)
        val binding = FragmentOneBinding.bind(view)
//        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        val viewModel = OneViewModel()
        val adapter = CustomAdapter(
            object : OnItemClickListener {
                override fun itemClick(item: Item) {
                    viewModel.goToRepositoryFragment(item, findNavController())
                }
            }
        )

        viewModel.bindingHandler(view, context, viewModel, adapter, lifecycleScope, binding)

    }

}

