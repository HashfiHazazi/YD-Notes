package com.ergophile.yd_notes.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergophile.yd_notes.KotprefLocalStorage
import com.ergophile.yd_notes.R
import com.ergophile.yd_notes.RouteName
import com.ergophile.yd_notes.YDNotesApplication
import com.ergophile.yd_notes.helper.viewModelFactoryHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    goToDetail: (id: Int?) -> Unit,
    goToProfile: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = viewModelFactoryHelper {
        HomeViewModel(YDNotesApplication.appModule.getRepository)
    })
) {
    val dashedBorder =
        Stroke(width = 4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f))
    val primaryColor = colorScheme.primary

    val homeState = viewModel.homeState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getListNotes(uid = KotprefLocalStorage.userUid)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "YD-Notes",
                        color = colorScheme.primary,
                        style = typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    goToDetail(null)
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new note")
            }
        }
    ) { innerPadding ->
        homeState.value.DisplayResult(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            onLoading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(64.dp), strokeWidth = 12.dp)
                }
            },
            onSuccess = { userNotes ->
                LazyColumn{
                    item {
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Hello, ",
                                style = typography.titleLarge.copy(color = colorScheme.onBackground)
                            )
                            Text(
                                text = KotprefLocalStorage.username,
                                style = typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Image(
                                modifier = modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.hello_icon),
                                contentDescription = "Greeting Icon"
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = { goToProfile() }) {
                                Icon(
                                    modifier = modifier.size(48.dp),
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Account Button",
                                    tint = colorScheme.onBackground
                                )
                            }
                        }
                    }
                    items(userNotes) {
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    goToDetail(it.id)
                                }
                                .drawBehind {
                                    drawRoundRect(
                                        color = primaryColor,
                                        style = dashedBorder,
                                        cornerRadius = CornerRadius(8.dp.toPx())
                                    )
                                }
                        ) {
                            Column(
                                modifier = modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = it.titleNotes,
                                    style = typography.titleMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = it.notesContent,
                                    style = typography.bodySmall,
                                    maxLines = 3,
                                    textAlign = TextAlign.Justify,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            },
            onError = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Notes List Error!",
                        style = typography.titleLarge.copy(color = Color.Red)
                    )
                }
            })

    }


}