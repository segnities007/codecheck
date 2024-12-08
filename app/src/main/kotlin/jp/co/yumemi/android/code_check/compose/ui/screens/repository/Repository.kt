package jp.co.yumemi.android.code_check.compose.ui.screens

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jp.co.yumemi.android.code_check.ui.two.TwoFragmentArgs

//fragment_twoのUI
@Composable
fun Repository(
    args: TwoFragmentArgs,
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        RepositoryImage(args.item.ownerIconUrl, args.item.name)
        RepositoryInfo(args)
    }
}

@Composable
private fun RepositoryImage(
    url: String,
    name: String,
){
    val padding = 32
    val fontSize = 32
    val imageSize = 160
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        AsyncImage(model = url, contentDescription = name, modifier = Modifier.size(imageSize.dp))
        Spacer(modifier = Modifier.padding((padding/2).dp))
        Text(name, fontSize = fontSize.sp)
    }
}

@Composable
private fun RepositoryInfo(
    args: TwoFragmentArgs,
){
    val item = args.item
    val language = item.language
    val starText = "${item.stargazersCount} stars"
    val watcherText = "${item.watchersCount} watchers"
    val forkText = "${item.forksCount} forks"
    val issueText = "${item.openIssuesCount} open issues"
    val list = listOf(
        language,
        starText,
        watcherText,
        forkText,
        issueText,
    )
    val padding = 12
    val fontSize = 18
    val height = 300

    Box(
        modifier = Modifier.fillMaxWidth(),
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
                .padding(horizontal = (padding*4).dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){
                for(i in 0 until list.size)
                    Text(
                        text = list[i],
                        modifier = Modifier.padding(padding.dp),
                        fontSize = fontSize.sp
                    )
            }
        }
    }

}