import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class Candidate(
    val name: String,
    var votes: Int = 0,
    val imageUrl: String
)

@Composable
fun ModernVotingScreen() {
    var candidates by remember {
        mutableStateOf(
            listOf(
                Candidate("Alice Johnson", imageUrl = "https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436178.jpg?t=st=1728280562~exp=1728284162~hmac=4b1ce8fc3255c42e1b0997ae955102d281354e2cf7b78108c0030cbf9f6711f8&w=1380"),
                Candidate("Bob Smith", imageUrl = "https://img.freepik.com/free-psd/3d-rendering-avatar_23-2150833560.jpg?t=st=1728280523~exp=1728284123~hmac=f8df82f860f30cd3ac31b02d3c7a127fefb969c9e52c7a2c3e42b5cf8038c4f4&w=1380"),
                Candidate("Charlie Brown", imageUrl = "https://img.freepik.com/free-psd/3d-illustration-human-avatar-profile_23-2150671142.jpg?t=st=1728280488~exp=1728284088~hmac=2dd5d3b168de6e7d0d4f2e54633bf85b7a7c7348d1d7afe8268d57b6feaee579&w=1380"),
                Candidate("Diana Ross", imageUrl = "https://img.freepik.com/free-psd/3d-render-avatar-character_23-2150611768.jpg?t=st=1728280612~exp=1728284212~hmac=3dcc86d94e4450d9cce0db52d2fad2d1e513bf497ed3af5204d6615b6ef8dde1&w=1380")
            )
        )
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF3F51B5),
            secondary = Color(0xFF03A9F4),
            background = Color(0xFFF5F5F5),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF333333)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Pemilihan Ketua Osis",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(candidates) { candidate ->
                        ModernCandidateCard(
                            candidate = candidate,
                            onVote = {
                                candidates = candidates.map {
                                    if (it.name == candidate.name) it.copy(votes = it.votes + 1) else it
                                }
                            }
                        )
                    }
                }

                ModernVoteResults(candidates)
            }
        }
    }
}

@Composable
fun ModernCandidateCard(candidate: Candidate, onVote: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = candidate.imageUrl,
                contentDescription = "Candidate Image",
                modifier = Modifier
                    .width(150.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = candidate.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Button(
                    onClick = onVote,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Vote",
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        tint = Color.White
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Vote",  color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ModernVoteResults(candidates: List<Candidate>) {
    Text(
        text = "Hasil Suara",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(vertical = 16.dp)
    )
    candidates.sortedByDescending { it.votes }.forEach { candidate ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = candidate.name,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Card(
                shape = RoundedCornerShape(50),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "${candidate.votes}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModernVotingPreview() {
    ModernVotingScreen()
}