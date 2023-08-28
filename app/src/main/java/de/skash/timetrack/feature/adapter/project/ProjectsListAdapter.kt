package de.skash.timetrack.feature.adapter.project

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.skash.timetrack.core.model.Project
import de.skash.timetrack.databinding.ListItemProjectBinding

class ProjectsListAdapter(
    private val onProjectClicked: (Project) -> Unit
) : ListAdapter<Project, ProjectViewHolder>(ProjectsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemProjectBinding.inflate(inflater, parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onProjectClicked)
    }
}

class ProjectViewHolder(
    private val binding: ListItemProjectBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(project: Project, onProjectClicked: (Project) -> Unit) {
        binding.titleTextView.text = project.title
        binding.colorDotImageView.setColorFilter(Color.parseColor(project.color))

        binding.root.setOnClickListener {
            onProjectClicked(project)
        }
    }
}

class ProjectsDiffUtil : ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem == newItem
    }
}