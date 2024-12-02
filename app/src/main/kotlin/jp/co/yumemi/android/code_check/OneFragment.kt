/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import kotlinx.coroutines.launch

class OneFragment: Fragment(R.layout.fragment_one){

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireActivity().application
        val binding = FragmentOneBinding.bind(view)
        val viewModel = OneViewModel(context)
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)

        val adapter= CustomAdapter(
            object : CustomAdapter.OnItemClickListener{
                override fun itemClick(item: Item){
                    goToRepositoryFragment(item, findNavController())
                }
            }
        )

        bindingHandler(binding, viewModel, adapter, lifecycleScope, layoutManager, dividerItemDecoration)

    }

}

private fun goToRepositoryFragment(
    item: Item,
    findNavController: NavController,
) {
    val action= OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item)
    findNavController.navigate(action)
}

private fun bindingHandler(
    binding: FragmentOneBinding,
    viewModel: OneViewModel,
    adapter: CustomAdapter,
    lifecycleScope: LifecycleCoroutineScope,
    layoutManager: RecyclerView.LayoutManager,
    dividerItemDecoration: DividerItemDecoration,
){
    binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
        if (action == EditorInfo.IME_ACTION_SEARCH) {
            val query = editText.text.toString()
            performSearch(query, viewModel, adapter, lifecycleScope)
            true
        } else {
            false
        }
    }

    binding.recyclerView.also{
        it.layoutManager = layoutManager
        it.addItemDecoration(dividerItemDecoration)
        it.adapter = adapter
    }
}

private fun performSearch(
    query: String,
    viewModel: OneViewModel,
    adapter: CustomAdapter,
    lifecycleScope: LifecycleCoroutineScope,
) {
    lifecycleScope.launch {
        val searchResult = viewModel.searchResults(query)
        adapter.submitList(searchResult)
    }
}

val diff_util = object: DiffUtil.ItemCallback<Item>(){
    override fun areItemsTheSame(
        oldItem: Item,
        newItem: Item,
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: Item,
        newItem: Item,
    ): Boolean {
        return oldItem == newItem
    }

}
