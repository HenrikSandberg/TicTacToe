package com.example.tictactoe.model
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.tictactoe.R
import com.example.tictactoe.controller.PlayerModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.tictactoe.controller.Player
import kotlinx.android.synthetic.main.fragment_choose_player.*


class ChoosePlayerFragment : Fragment() {
    private lateinit var playerModel: PlayerModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerModel = ViewModelProviders.of(this).get(PlayerModel::class.java)
        playerModel.allPlayers.observe(this, Observer {
            val personAdapter = ArrayAdapter<Any>(
                (activity as MainActivity),
                android.R.layout.simple_spinner_dropdown_item
            )
            personAdapter.clear()
            it.forEach {player ->
                println(player.name)
                personAdapter.add(player.name)
                choose_player_spinner.adapter = personAdapter
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        add_player_button.setOnClickListener {
            if (add_player_text.text.isNotEmpty()) {
                var isInList = false
                (0 until choose_player_spinner.childCount).forEach {
                    if (it.toString() == add_player_text.text.toString()){
                        isInList = true
                    }

                }
                if (!isInList) {
                    println("IS NOT IN LIST WILL NOW BE ADDED")
                    //playerModel.insert(Player(add_player_text.text.toString(), 0, 0))
                }
            }
        }
    }

}
