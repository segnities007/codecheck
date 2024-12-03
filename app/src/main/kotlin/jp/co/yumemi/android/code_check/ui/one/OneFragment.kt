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
import jp.co.yumemi.android.code_check.model.Item

class OneFragment: Fragment(R.layout.fragment_one){

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireActivity().application
        val viewModel =
            OneViewModel(context, FragmentOneBinding.bind(view), LinearLayoutManager(context))
        val adapter = CustomAdapter(
            object : CustomAdapter.OnItemClickListener {
                override fun itemClick(item: Item) {
                    viewModel.goToRepositoryFragment(item, findNavController())
                }
            }
        )

        viewModel.bindingHandler(viewModel, adapter, lifecycleScope)
    }

}

