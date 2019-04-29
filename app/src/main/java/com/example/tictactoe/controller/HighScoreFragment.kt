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
import com.example.tictactoe.model.PlayerRecyclerViewAdapter
import com.example.tictactoe.model.database.Player
import com.example.tictactoe.model.database.PlayerModel
import kotlinx.android.synthetic.main.fragment_person_list.view.*

class HighScoreFragment : Fragment() {
    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var playerModel: PlayerModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_list, container, false)

        playerModel = ViewModelProviders.of(this).get(PlayerModel::class.java)
        playerModel.allPlayers.observe (this, Observer{ listOfPlayers ->
            if (view.recyclerview != null) {
                with (view.recyclerview) {
                    layoutManager = LinearLayoutManager (activity)
                    adapter = PlayerRecyclerViewAdapter(
                        listOfPlayers.sortedByDescending { player -> player.score },
                        activity as MainActivity
                    )
                    return@Observer
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
