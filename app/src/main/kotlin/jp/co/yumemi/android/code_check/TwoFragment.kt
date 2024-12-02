/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    private var binding: FragmentTwoBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentTwoBinding.bind(view)
        addInfo(args, _binding)
    }
}

fun addInfo(
    args: TwoFragmentArgs,
    binding: FragmentTwoBinding,
){
    val item = args.item
    val starText = "${item.stargazersCount} stars"
    val watcherText = "${item.watchersCount} watchers"
    val forkText = "${item.forksCount} forks"
    val issueText = "${item.openIssuesCount} open issues"

    binding.ownerIconView.load(item.ownerIconUrl)
    binding.nameView.text = item.name
    binding.languageView.text = item.language
    binding.starsView.text = starText
    binding.watchersView.text = watcherText
    binding.forksView.text = forkText
    binding.openIssuesView.text = issueText
}
