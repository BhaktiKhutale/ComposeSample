package com.example.composesample

import android.media.Image
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.composesample.data.TvShowList
import com.example.composesample.model.TvShow
import com.example.composesample.ui.theme.ComposeSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeSampleTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding() // Pushes content below status bar
                        .navigationBarsPadding() // Pushes content above (or away from) nav bar
                ) {
                    DisplayTvShows {
                        startActivity(InfoActivity.newIntent(this@MainActivity, it))
                    }
                }

            }
        }
    }
}
@Composable
fun DisplayTvShows(selectedItem: (TvShow) -> (Unit)) {
    val tvShows = rememberSaveable { TvShowList.tvShows }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = tvShows,
            itemContent = {
                TvShowListItem(tvShow = it,  selectedItem)
            }
        )
    }

}

@Composable
fun TvShowImage(tvShow: TvShow){
   Image(
       painter = painterResource(id = tvShow.imageId),
       contentDescription = null,
       contentScale = ContentScale.Crop,
       modifier = Modifier
           .padding(4.dp)
           .height(140.dp)
           .width(100.dp)
           .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
   )
}

@Composable
fun TvShowListItem(tvShow: TvShow, selectedItem: (TvShow) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .clickable { selectedItem(tvShow) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            TvShowImage(tvShow = tvShow)
            Column {
                Text(text = tvShow.name, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tvShow.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = tvShow.year.toString(), style = MaterialTheme.typography.bodyLarge)
                    Text(text = tvShow.rating.toString(), style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
