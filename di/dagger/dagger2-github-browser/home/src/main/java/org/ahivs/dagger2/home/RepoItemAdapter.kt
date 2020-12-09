package org.ahivs.dagger2.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.ahivs.dagger2.home.RepoItemAdapter.RepoItemViewHolder
import org.ahivs.dagger2.home.databinding.RepoItemBinding
import org.ahivs.dagger2.home.list.RepoItem

class RepoItemAdapter :
    RecyclerView.Adapter<RepoItemViewHolder>() {

    private val repoItems: MutableList<RepoItem> = mutableListOf()

    fun setItems(items: List<RepoItem>) {
        repoItems.clear()
        repoItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val binding = RepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoItemViewHolder(binding)
    }

    override fun getItemCount(): Int = repoItems.size

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.bind(repoItems[position])
    }

    class RepoItemViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repoItem: RepoItem) {
            binding.apply {
                txtDescription.text = repoItem.description
                txtName.text = repoItem.name
                txtForkCount.text = repoItem.forkCount.toString()
                txtStartCount.text = repoItem.starCount.toString()
            }
        }

    }
}