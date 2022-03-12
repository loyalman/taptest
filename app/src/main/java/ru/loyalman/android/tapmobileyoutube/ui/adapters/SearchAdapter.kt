package ru.loyalman.android.tapmobileyoutube.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.loyalman.android.remote.dto.VideoResponse
import ru.loyalman.android.tapmobileyoutube.databinding.ItemResultBinding


class SearchAdapter(
    private val onPlay: (url: String) -> Unit,
) : ListAdapter<ResultItem, SearchViewHolder>(ResultDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding = binding, onPlay = onPlay)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SearchViewHolder(
    private val binding: ItemResultBinding,
    private val onPlay: (url: String) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ResultItem) {
        Glide.with(binding.thumbnail)
            .load(item.pictureUrl)
            .into(binding.thumbnail)
        binding.thumbnail.setOnClickListener {
            onPlay(item.videoUrl)
        }
    }
}

data class ResultItem(
    val id: String,
    val videoUrl: String,
    val pictureUrl: String,
)

class ResultDiff : DiffUtil.ItemCallback<ResultItem>() {
    override fun areItemsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
        return oldItem == newItem
    }

}

fun VideoResponse.toViewItem(): ResultItem {
    return ResultItem(
        id = url,
        videoUrl = "https://www.youtube.com/embed/$url",
        pictureUrl = pictureUrl
    )
}