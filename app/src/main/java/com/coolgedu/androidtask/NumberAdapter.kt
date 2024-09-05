package com.coolgedu.androidtask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class NumberAdapter : RecyclerView.Adapter<NumberAdapter.LegendsViewHolder>() {

    private var highlightedPositions: Set<Int> = emptySet()

    class LegendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionNoCard: MaterialCardView = itemView.findViewById(R.id.legends_question_background)
        val questionNoText: TextView = itemView.findViewById(R.id.legends_question_no)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegendsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return LegendsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: LegendsViewHolder, position: Int) {
        val questionNumber = position + 1
        holder.questionNoText.text = questionNumber.toString()

        if (position in highlightedPositions) {
            holder.questionNoCard.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.number_bg))
            holder.questionNoText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        } else {
            holder.questionNoCard.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.questionNoText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }
    }

    fun updateHighlightedPositions(positions: Set<Int>) {
        highlightedPositions = positions
        notifyDataSetChanged()
    }
}
