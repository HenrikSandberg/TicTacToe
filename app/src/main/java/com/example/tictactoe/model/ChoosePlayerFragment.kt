package com.example.tictactoe.model
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.tictactoe.controller.PlayerModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tictactoe.R
import com.example.tictactoe.controller.Player
import kotlinx.android.synthetic.main.fragment_choose_player.*




class ChoosePlayerFragment : Fragment() {
    private lateinit var playerModel: PlayerModel
    private var players: MutableList<String> = mutableListOf()
    private val isThisPlayerOne = true
    private var shouldBeSelected: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerModel = ViewModelProviders.of(this).get(PlayerModel::class.java)
        playerModel.allPlayers.observe(this, Observer {listOfPlayers ->
            val personAdapter = ArrayAdapter<Any>(
                (activity as MainActivity),
                android.R.layout.simple_spinner_dropdown_item
            )
            personAdapter.clear()
            listOfPlayers.forEach { player ->
                personAdapter.add(player.name)
                players.add(player.name)
            }
            choose_player_spinner.adapter = personAdapter

            if (shouldBeSelected != null) {
                choose_player_spinner.setSelection(personAdapter.getPosition(shouldBeSelected))
            }
        })
    }

    private fun doesNotContain():Boolean = players.none { (it == add_player_text.text.toString()) }

    private fun handleText() {
        if (add_player_text.text.isNotEmpty() && doesNotContain()) {
            val playerName = add_player_text.text.toString()
            playerModel.insert( Player(playerName, 0, 0) )
            shouldBeSelected = playerName
            mainLayout.requestFocus()
        }
        add_player_text.clearFocus()
        add_player_text.text.clear()
        add_player_text.clearAnimation()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*ADD NEW PLAYER BUTTON PRESS*/
        add_player_text.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                handleText()
                return@OnKeyListener true
            }
            false
        })

        add_player_button.setOnClickListener { handleText() }

        /*TO NEXT FRAGMENT*/
        bottom_button_on_player_select.setOnClickListener {
            if (isThisPlayerOne) {
                (activity as MainActivity).setPlayerOne(choose_player_spinner.selectedItem.toString())
            } else {
                (activity as MainActivity).setPlayerTwo(choose_player_spinner.selectedItem.toString())
            }
        }
    }

}
