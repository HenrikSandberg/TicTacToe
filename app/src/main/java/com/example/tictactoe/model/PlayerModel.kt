package com.example.tictactoe.model
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tictactoe.controller.PlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
class PlayerModel(application: Application): AndroidViewModel(application){
    private val repository: PlayerRepository
    val allPlayers: LiveData<List<Player>>

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val playerDAO = AppDatabase.getDatabase(application.applicationContext).getPlayerDAO()
        repository = PlayerRepository(playerDAO)
        allPlayers = repository.allPlayersLive
    }

    fun insert(player: Player) = scope.launch(Dispatchers.IO) { repository.insert(player) }

    fun delete() = scope.launch(Dispatchers.IO){ repository.deleteAll() }

    fun update(player: Player) = scope.launch(Dispatchers.IO){ repository.update(player) }
}