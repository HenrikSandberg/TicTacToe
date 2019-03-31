package com.example.tictactoe.controller

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tictactoe.R
import com.example.tictactoe.model.Player
import com.example.tictactoe.model.PlayerModel

class HighScoreFragment : Fragment() {
    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var playerModel: PlayerModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_list, container, false)
        // Set the adapter
        playerModel = ViewModelProviders.of(this).get(PlayerModel::class.java)
        playerModel.allPlayers.observe (this, Observer{ listOfPlayers ->
            if (view is RecyclerView) {
                with (view) {
                    layoutManager = LinearLayoutManager (activity)
                    adapter = PlayerRecyclerViewAdapter ( // TODO: not sure i should sort by  player.score + player.wins || player.score || player.wins
                        listOfPlayers.sortedByDescending { player -> player.score },
                        activity as MainActivity
                    )
                }
            }
        })
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener { // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Player?)
    }
}
