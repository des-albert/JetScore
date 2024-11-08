package org.db.jetscore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.db.jetscore.ui.theme.JetScoreTheme
import org.db.jetscore.ui.theme.provider

val fontName = GoogleFont("Orbitron")

val fontFamily = FontFamily(
  Font(googleFont = fontName, fontProvider = provider)
)

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      JetScoreTheme {
        Scaffold(
          topBar = {
            ScoreTopBar()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreTopBar() {
  TopAppBar(
    title = {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        Text(fontFamily = fontFamily, text = "Domino Scoreboard")
      }
    },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
      containerColor = Color(0xFF2F607E),
      titleContentColor = Color(0xFF7AD092)
    )
  )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ScoreSummary(
  modifier: Modifier = Modifier,
  playersViewModel: MainViewModel = viewModel()
) {

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
            modifier = modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(fontFamily = fontFamily, text = score.name, fontSize = 24.sp, color = Color.Cyan)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = score.play.toString(), fontSize = 24.sp, color = Color.Yellow)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = score.out.toString(), fontSize = 24.sp, color = Color.Yellow)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = score.total.toString(), fontSize = 32.sp, color = Color.Yellow)
            Spacer(modifier = Modifier.height(10.dp))
            ElevatedButton(
              colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFF7AD092), // Background color
                contentColor = Color.Blue   // Text color
              ),
              modifier = Modifier.size(80.dp, 40.dp),
              onClick = {
                players[index] = players[index].copy(
                  play = players[index].play + inc,
                  prevPlay = inc,
                  prevOut = 0,
                  total = players[index].total + inc
                )
                inc = 0
              }
            ) {
              Text("Play")
            }
            ElevatedButton(
              colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFF4C9390), // Background color
                contentColor = Color.Blue   // Text color
              ),
              onClick = {
                players[index] = players[index].copy(
                  out = players[index].out + inc,
                  prevPlay = 0,
                  prevOut = inc,
                  total = players[index].total + inc
                )
                inc = 0
              }
            ) {
              Text("Out")
            }
            ElevatedButton(
              colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFF3A5E5B), // Background color
                contentColor = Color(0xFF8BC34A),   // Text color
              ),
              onClick = {
                players[index] = players[index].copy(
                  play = players[index].play - players[index].prevPlay,
                  out = players[index].out - players[index].prevOut,
                  total = players[index].total - players[index].prevPlay - players[index].prevOut,
                  prevPlay = 0, prevOut = 0,
                )
              }
            ) {
              Text("Cancel")
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
              contentPadding = PaddingValues(
                start = 4.dp,
                top = 8.dp,
                end = 4.dp,
                bottom = 8.dp),
              colors = ButtonDefaults.elevatedButtonColors(
              containerColor = Color(0xFF819682), // Background color
              contentColor = Color(0xFF851236)   // Text color
              ),
              onClick = {
                inc = 5 * (i + j)
              },
              modifier = Modifier
                .size(75.dp, 40.dp)
                .padding(3.dp)
            ) {
              Text(
                text = "${5 * (i + j)}",
                fontSize = 14.sp
              )
            }
          }
        }
      }
      Spacer(modifier = Modifier.height(50.dp))
      Row {
        for (score in players) {
          if (score.total >= 500)
            Box(
              modifier = Modifier.fillMaxSize(),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "The Winner is ${score.name}",
                fontFamily = fontFamily, fontSize = 32.sp, color = Color.Cyan
              )
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
  JetScoreTheme {
    Scaffold(
      topBar = {
        ScoreTopBar()
      },
      modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
      ScoreSummary(
        modifier = Modifier.padding(innerPadding)
      )
    }
  }
}