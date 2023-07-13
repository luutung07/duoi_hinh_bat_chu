package com.example.findword.presentation.main

import android.annotation.SuppressLint
import android.util.Log
import android.util.Size
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findword.R
import com.example.findword.databinding.WordItemBinding
import com.example.findword.extensions.getAppDimension
import com.example.findword.presentation.util.AppConfig
import kotlin.math.roundToInt

class WordAdapter :
    ListAdapter<QuestionDisplay, WordAdapter.WordVH>(WordDiffCallBack()) {

    private val TAG = "WordAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordVH {
        return WordVH(WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WordVH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class WordVH(private val binding: WordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: QuestionDisplay) {
            binding.tvWord.text = data.word
        }
    }

    class WordDiffCallBack : DiffUtil.ItemCallback<QuestionDisplay>() {
        override fun areItemsTheSame(oldItem: QuestionDisplay, newItem: QuestionDisplay): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: QuestionDisplay,
            newItem: QuestionDisplay
        ): Boolean {
            return oldItem == newItem
        }
    }
}