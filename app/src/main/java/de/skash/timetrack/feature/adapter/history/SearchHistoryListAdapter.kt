package de.skash.timetrack.feature.adapter.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.skash.timetrack.core.model.SearchHistoryEntry
import de.skash.timetrack.databinding.ListItemSearchHistoryBinding

class SearchHistoryListAdapter(
    private val onEntryClicked: (SearchHistoryEntry) -> Unit
) : ListAdapter<SearchHistoryEntry, SearchHistoryViewHolder>(SearchHistoryEntryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSearchHistoryBinding.inflate(inflater, parent, false)
        return SearchHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onEntryClicked)
    }
}

class SearchHistoryViewHolder(
    private val binding: ListItemSearchHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(entry: SearchHistoryEntry, onEntryClicked: (SearchHistoryEntry) -> Unit) {
        binding.textView.text = entry.title

        binding.root.setOnClickListener {
            onEntryClicked(entry)
        }
    }
}

class SearchHistoryEntryDiffUtil : ItemCallback<SearchHistoryEntry>() {
    override fun areItemsTheSame(
        oldItem: SearchHistoryEntry,
        newItem: SearchHistoryEntry
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: SearchHistoryEntry,
        newItem: SearchHistoryEntry
    ): Boolean {
        return oldItem == newItem
    }
}