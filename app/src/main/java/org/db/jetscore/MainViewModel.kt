package org.db.jetscore

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

data class Score(
    var name: String,
    var play: Int,
    var out: Int,
    var total: Int
)

class MainViewModel : ViewModel() {

    val players: SnapshotStateList<Score> = mutableStateListOf(
      Score("DB", 0, 0, 0),
      Score("Bo", 0, 0, 0),
      Score("Steve", 0, 0, 0)
    )

}