package org.db.jetscore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.db.jetscore.ui.theme.JetScoreTheme


class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      JetScoreTheme {
        Scaffold(
          topBar = {
            TopAppBar(
              title = {
                Box(
                  modifier = Modifier.fillMaxSize(),
                  contentAlignment = Alignment.Center
                ) {
                  Text("Domino Scoreboard")
                }
              },
              colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF6200EE),
                titleContentColor = Color(0xFFC7D07A)
              )
            )
          },
          modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
          ScoreSummary(
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun ScoreSummary(modifier: Modifier = Modifier,
                 playersViewModel: MainViewModel = viewModel()) {

  val players = playersViewModel.players

  var inc: Int = 0

  Surface(color = MaterialTheme.colorScheme.primary) {
    Column(modifier = Modifier.fillMaxHeight()) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
      )
      {
        for ((index, score) in players.withIndex()) {
          Column(
            modifier = modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(text = score.name, fontSize = 30.sp, color = Color.Cyan)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = score.play.toString(), fontSize = 24.sp, color = Color.Yellow)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = score.out.toString(), fontSize = 24.sp, color = Color.Yellow)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = score.total.toString(), fontSize = 30.sp, color = Color.Yellow)
            Spacer(modifier = Modifier.height(10.dp))
            ElevatedButton(
              colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.LightGray, // Background color
                contentColor = Color.Blue   // Text color
              ),
              modifier = Modifier.size(80.dp, 40.dp),
              onClick = {
                players[index] = players[index].copy(play = players[index].play + inc)
                players[index] = players[index].copy(total = players[index].total + inc)
              }
            ) {
              Text("Play")
            }
            ElevatedButton(
              colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.LightGray, // Background color
                contentColor = Color.Red   // Text color
              ),
              onClick = {
                players[index] = players[index].copy(out = players[index].out + inc)
                players[index] = players[index].copy(total = players[index].total + inc)
              }
            ) {
              Text("Out")
            }
          }
        }
      }
      for (j in 0..20 step 5) {

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
        ) {
          for (i in 1..5) {
            ElevatedButton(
              colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.DarkGray, // Background color
                contentColor = Color.Yellow   // Text color
              ),
              onClick = { inc = 5 * (i + j) },
              modifier = Modifier
                .size(75.dp, 40.dp)
                .padding(2.dp)
            ) {
              Text(
                text = "${5 * (i + j)}",
                fontSize = 12.sp
              )
            }
          }
        }

      }

    }
  }
}


@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyAppPreview() {
  JetScoreTheme  {
    Scaffold(
      topBar = {
        TopAppBar(
          title = {
            Box(
              modifier = Modifier.fillMaxSize(),
              contentAlignment = Alignment.Center
            ) {
              Text("Domino Scoreboard")
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF6200EE),
            titleContentColor = Color(0xFFC7D07A)
          )

        )
      },
      modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
      ScoreSummary(
        modifier = Modifier.padding(innerPadding)
      )
    }
  }
}