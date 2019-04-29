package com.example.tictactoe.model

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tictactoe.R
import com.example.tictactoe.controller.HighScoreFragment
import com.example.tictactoe.model.database.Player
import kotlinx.android.synthetic.main.fragment_person.view.*

class PlayerRecyclerViewAdapter(
    private val mValues: List<Player>,
    private val mListener: HighScoreFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<PlayerRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Player
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.fragment_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = (position+1).toString()
        holder.mNameView.text = item.name
        holder.mWinView.text = item.wins.toString()
        holder.mScoreView.text = item.score.toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mNameView: TextView = mView.name
        val mWinView: TextView = mView.wins
        val mScoreView: TextView = mView.score

        override fun toString(): String {
            return super.toString() + " '" + mWinView.text + "'"
        }
    }
}
