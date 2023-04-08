package com.zrq.apackage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zrq.apackage.databinding.ItemPackageBinding

class PkgAdapter(
    private val context: Context,
    private val list: MutableList<Package>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<PkgAdapter.InnerHolder>() {

    class InnerHolder(val binding: ItemPackageBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val binding = ItemPackageBinding.inflate(LayoutInflater.from(context), parent, false)
        return InnerHolder(binding)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvName.text = item.name
            ivIcon.setImageDrawable(item.drawable)
            root.setOnClickListener { onClick(position) }
        }
    }

    override fun getItemCount(): Int = list.size


}