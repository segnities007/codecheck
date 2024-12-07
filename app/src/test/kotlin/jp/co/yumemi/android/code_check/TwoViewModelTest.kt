package jp.co.yumemi.android.code_check

import androidx.lifecycle.ViewModel
import coil.load
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding
import jp.co.yumemi.android.code_check.ui.two.TwoFragmentArgs

class TwoViewModelTest(

): ViewModel(){

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

}