/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.one

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.compose.ui.Theme
import jp.co.yumemi.android.code_check.compose.ui.screens.repository.Repository
import jp.co.yumemi.android.code_check.compose.ui.screens.search.Search
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding

class OneFragment: Fragment(R.layout.fragment_one){

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        val viewModel = OneViewModel()
        val composeView = view.findViewById<ComposeView>(R.id.search)
        composeView.setContent {
            Theme {
                Search(view, context){item -> viewModel.goToRepositoryFragment(item, findNavController())}
            }
        }

    }

}

