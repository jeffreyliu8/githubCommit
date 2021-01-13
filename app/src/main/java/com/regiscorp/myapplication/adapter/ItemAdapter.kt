package com.regiscorp.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.regiscorp.myapplication.databinding.ListItemBinding
import com.regiscorp.myapplication.model.CommitData


class ItemAdapter :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    private var mList = mutableListOf<CommitData>()

    fun updateList(list: List<CommitData>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(mList[position])

    inner class MyViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommitData) = with(itemView) {
            binding.authorTextView.text = item.commit.author.name
            binding.commitHashTextView.text = item.sha
            binding.commitMessageTextView.text = item.commit.message
        }
    }
}