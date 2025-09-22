package com.example.dailyplanet.ui.composables

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // <-- THIS IMPORT WAS MISSING
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dailyplanet.R
import com.example.dailyplanet.models.Article
import com.example.dailyplanet.ui.viewmodel.NewsViewModel

@Composable
fun NewsCard(
    article: Article,
    viewModel: NewsViewModel,
    isFavorite: Boolean
) {
    val context = LocalContext.current
    val primaryColor = MaterialTheme.colorScheme.primary.toArgb()

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                article.url.let { url ->
                    val builder = CustomTabsIntent.Builder()
                    builder.setDefaultColorSchemeParams(
                        androidx.browser.customtabs.CustomTabColorSchemeParams.Builder()
                            .setToolbarColor(primaryColor)
                            .build()
                    )
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse(url))
                }
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .build(),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.source?.name ?: "Unknown Source",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Red
                )
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = article.description ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Row {
                        IconButton(onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, article.url)
                                putExtra(Intent.EXTRA_TITLE, article.title)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(onClick = {
                            if (isFavorite) {
                                viewModel.deleteArticle(article)
                            } else {
                                viewModel.saveArticle(article)
                            }
                        }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}