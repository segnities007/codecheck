/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.two

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.compose.ui.components.RepositoryList
import jp.co.yumemi.android.code_check.compose.ui.screens.Repository
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

//特定のrepositoryの詳細について表示する画面
class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()
    private var binding: FragmentTwoBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

//        val viewModel = TwoViewModel()
        binding = FragmentTwoBinding.bind(view)
//        viewModel.addInfo(args, _binding)
        val composeView = view.findViewById<ComposeView>(R.id.repository)
        composeView.setContent {
            Repository(args)
        }

    }

    //メモリリーク対策
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
