package com.example.findword.presentation.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findword.databinding.WordItemBinding
import com.example.findword.extensions.INT_DEFAULT
import com.example.findword.extensions.STRING_DEFAULT
import com.example.findword.extensions.gone
import com.example.findword.extensions.setOnSafeClick
import com.example.findword.extensions.show

class WordAdapter :
    ListAdapter<QuestionDisplay, WordAdapter.WordVH>(WordDiffCallBack()) {

    companion object {
        private const val UPDATE_OPTION_PAYLOAD = "UPDATE_OPTION_PAYLOAD"
        private const val UPDATE_STATE_SELECT_PAYLOAD = "UPDATE_STATE_SELECT_PAYLOAD"
    }

    var listener: IWordListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordVH {
        return WordVH(WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WordVH, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(holder: WordVH, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.onBind(getItem(position), payloads)
        }
    }

    inner class WordVH(private val binding: WordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnSafeClick {
                val item = getItem(absoluteAdapterPosition)
                when (item.type) {
                    WORD_TYPE.OPTION -> {
                        if (!item.isSelect) {
                            listener?.onSelectOption(
                                item.character ?: STRING_DEFAULT,
                                item.type ?: WORD_TYPE.ANSWER,
                                absoluteAdapterPosition
                            )
                        } else {
                            listener?.onRemoveOption(
                                absoluteAdapterPosition,
                                type = item.type ?: WORD_TYPE.ANSWER
                            )
                        }
                    }

                    WORD_TYPE.SELECTED_OPTION -> {
                        listener?.onRemoveOption(
                            absoluteAdapterPosition,
                            type = item.type ?: WORD_TYPE.ANSWER,
                            item.character
                        )
                    }

                    else -> {}
                }
            }
        }

        fun onBind(data: QuestionDisplay) {

            val params = binding.flWordRoot.layoutParams as ViewGroup.LayoutParams
            params.width = data.size?.width ?: INT_DEFAULT
            params.height = data.size?.height ?: INT_DEFAULT
            binding.flWordRoot.layoutParams = params

            showTvWord(data)
            stateSelect(data)
        }

        fun onBind(data: QuestionDisplay, payloads: MutableList<Any>) {
            (payloads.firstOrNull() as? List<*>)?.forEach {
                when (it) {
                    UPDATE_OPTION_PAYLOAD -> {
                        showTvWord(data)
                    }

                    UPDATE_STATE_SELECT_PAYLOAD -> {
                        stateSelect(data)
                    }
                }
            }
        }

        private fun stateSelect(data: QuestionDisplay) {
            if (data.isSelect) {
                binding.tvWord.gone()
            } else {
                binding.tvWord.show()
            }
        }

        private fun showTvWord(data: QuestionDisplay) {
            if (data.type == WORD_TYPE.ANSWER) {
                binding.tvWord.text = STRING_DEFAULT
            } else {
                binding.tvWord.text = data.character
            }
        }
    }

    class WordDiffCallBack : DiffUtil.ItemCallback<QuestionDisplay>() {
        override fun areItemsTheSame(oldItem: QuestionDisplay, newItem: QuestionDisplay): Boolean {
            return oldItem.answerCorrect == newItem.answerCorrect
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: QuestionDisplay,
            newItem: QuestionDisplay
        ): Boolean {
            return oldItem.type == newItem.type && oldItem.isSelect == newItem.isSelect
                    && oldItem.character == newItem.character
        }

        override fun getChangePayload(oldItem: QuestionDisplay, newItem: QuestionDisplay): Any? {
            val list = mutableListOf<Any>()

            if (oldItem.type != newItem.type || oldItem.character != newItem.character) {
                list.add(UPDATE_OPTION_PAYLOAD)
            }

            if (oldItem.isSelect != newItem.isSelect) {
                list.add(UPDATE_STATE_SELECT_PAYLOAD)
            }

            return list.ifEmpty { null }
        }
    }
}