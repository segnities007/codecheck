package jp.co.yumemi.android.code_check.ui.two

import androidx.lifecycle.ViewModel
import coil.load
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding
import jp.co.yumemi.android.code_check.model.Item

//TwoViewModelに使用する
class TwoViewModel(

): ViewModel(){

    fun addInfo(
        args: TwoFragmentArgs,
        binding: FragmentTwoBinding,
    ){
        val item = args.item
        val starText = "${Item.stargazersCount} stars"
        val watcherText = "${Item.watchersCount} watchers"
        val forkText = "${Item.forksCount} forks"
        val issueText = "${Item.openIssuesCount} open issues"

        binding.ownerIconView.load(Item.ownerIconUrl)
        binding.nameView.text = Item.name
        binding.languageView.text = Item.language
        binding.starsView.text = starText
        binding.watchersView.text = watcherText
        binding.forksView.text = forkText
        binding.openIssuesView.text = issueText
    }

}