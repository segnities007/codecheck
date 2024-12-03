package jp.co.yumemi.android.code_check.ui.one

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.model.Item

//OneFragmentで使用します
class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<Item, CustomAdapter.ViewHolder>(diff_util){

    interface OnItemClickListener{
        fun itemClick(item: Item)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        val textView: TextView = holder.itemView.findViewById<TextView>(R.id.repositoryNameView)
        textView.text = item.name

        holder.itemView.setOnClickListener{
            itemClickListener.itemClick(item)
        }
    }
}

private val diff_util = object: DiffUtil.ItemCallback<Item>(){

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