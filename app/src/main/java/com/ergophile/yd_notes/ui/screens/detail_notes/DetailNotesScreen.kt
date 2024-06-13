package com.ergophile.yd_notes.ui.screens.detail_notes

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergophile.yd_notes.R
import com.ergophile.yd_notes.YDNotesApplication
import com.ergophile.yd_notes.helper.viewModelFactoryHelper
import es.dmoral.toasty.Toasty
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailNotesScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    detailViewModel: DetailViewModel = viewModel(factory = viewModelFactoryHelper {
        DetailViewModel(YDNotesApplication.appModule.getRepository)
    }),
    idNote: Int
) {

    var showSaveNoteDialog by remember {
        mutableStateOf(false)
    }

    var confirmDeleteNoteDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val detailState = detailViewModel.detailState.collectAsState()

    val updateDetailState = detailViewModel.updateDetailState.collectAsState()

    val deleteNoteState = detailViewModel.deleteNoteState.collectAsState()

    LaunchedEffect(key1 = true) {
        detailViewModel.getDetail(idNotes = idNote)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = modifier.padding(horizontal = 16.dp),
                title = {
                    Text(
                        text = "Detail Note",
                        color = colorScheme.primary
                    )
                },
                navigationIcon = {
                    Card(
                        modifier = modifier.size(48.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        border = BorderStroke(1.5.dp, color = colorScheme.primary),
                        onClick = {
                            showSaveNoteDialog = showSaveNoteDialog != true
                        }
                    ) {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = modifier,
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Button",
                                tint = colorScheme.primary
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        confirmDeleteNoteDialog = true
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "Delete Note Button"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        detailState.value.DisplayResult(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            onLoading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(64.dp), strokeWidth = 12.dp)
                }
            },
            onSuccess = { userNotes ->
                Box(modifier = modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = modifier.fillMaxSize()
                    ) {
                        items(userNotes) {
                            var titleNotesValue by remember {
                                mutableStateOf(TextFieldValue(it.titleNotes))
                            }
                            var subtitleNotesValue by remember {
                                mutableStateOf(TextFieldValue(it.subtitleNotes))
                            }

                            var contentNotesValue by remember {
                                mutableStateOf(TextFieldValue(it.notesContent))
                            }
                            Column(
                                modifier = modifier.padding(paddingValues = innerPadding)
                            ) {
                                if (showSaveNoteDialog == true) {
                                    AlertDialog(
                                        modifier = modifier.shadow(elevation = 4.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        containerColor = Color.White,
                                        icon = {
                                            Icon(
                                                modifier = modifier.size(128.dp),
                                                painter = painterResource(id = R.drawable.outline_question_mark_24),
                                                contentDescription = "Ask Save Icon"
                                            )
                                        },
                                        title = {
                                            Text(
                                                text = "Save note changes?",
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        },
                                        onDismissRequest = {

                                        },
                                        confirmButton = {
                                            TextButton(
                                                onClick = {
                                                    goBack()
                                                    Toasty.error(
                                                        context,
                                                        "Note are not save",
                                                        Toast.LENGTH_LONG,
                                                        true
                                                    ).show()
                                                }
                                            ) {
                                                Text("don't save")
                                            }
                                            TextButton(
                                                onClick = {
                                                    detailViewModel.updateNoteDetail(
                                                        idNotes = idNote,
                                                        updateBody = buildJsonObject {
                                                            put("title_notes", titleNotesValue.text)
                                                            put(
                                                                "subtitle_notes",
                                                                subtitleNotesValue.text
                                                            )
                                                            put(
                                                                "notes_content",
                                                                contentNotesValue.text
                                                            )
                                                            put("modified_at", "now()")
                                                        }
                                                    )
                                                }
                                            ) {
                                                Text("save")
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(
                                                onClick = {
                                                    showSaveNoteDialog = false
                                                }
                                            ) {
                                                Text("cancel")
                                            }

                                        }
                                    )
                                }
                                if (confirmDeleteNoteDialog == true){
                                    AlertDialog(
                                        modifier = modifier.shadow(elevation = 4.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        containerColor = Color.White,
                                        icon = {
                                            Icon(
                                                modifier = modifier.size(128.dp),
                                                painter = painterResource(id = R.drawable.baseline_delete_24),
                                                contentDescription = "Delete Icon"
                                            )
                                        },
                                        title = {
                                            Text(
                                                text = "Delete this note?",
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        },
                                        onDismissRequest = {},
                                        confirmButton = {
                                            TextButton(
                                                onClick = {
                                                    detailViewModel.deleteNote(idNote = idNote)
                                                }
                                            ) {
                                                Text("confirm")
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(
                                                onClick = {
                                                    confirmDeleteNoteDialog = false
                                                }
                                            ) {
                                                Text("cancel")
                                            }

                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    modifier = modifier.padding(horizontal = 16.dp),
                                    text = "Notes title:",
                                    style = typography.titleMedium
                                )
                                TextField(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    value = titleNotesValue,
                                    onValueChange = { inputValue ->
                                        titleNotesValue = inputValue
                                    },
                                    colors = TextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                    ),
                                    textStyle = typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),

                                    )
                                Spacer(modifier = modifier.height(24.dp))
                                Text(
                                    modifier = modifier.padding(horizontal = 16.dp),
                                    text = "Notes subtitle:",
                                    style = typography.titleMedium
                                )
                                TextField(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    value = subtitleNotesValue,
                                    onValueChange = { inputValue ->
                                        subtitleNotesValue = inputValue
                                    },
                                    colors = TextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                    ),
                                    textStyle = typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),

                                    )
                                Spacer(modifier = modifier.height(24.dp))
                                Text(
                                    modifier = modifier.padding(horizontal = 16.dp),
                                    text = "Notes content:",
                                    style = typography.titleMedium
                                )
                                TextField(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    value = contentNotesValue,
                                    onValueChange = { inputValue ->
                                        contentNotesValue = inputValue
                                    },
                                    colors = TextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                    ),
                                    textStyle = typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),

                                    )

                            }
                        }
                    }
                    updateDetailState.value.DisplayResult(
                        onLoading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(64.dp),
                                    strokeWidth = 12.dp
                                )
                            }
                        },
                        onSuccess = {
                            Toasty.success(
                                context,
                                "Note saved successfully",
                                Toast.LENGTH_LONG,
                                true
                            ).show()
                            goBack()
                        },
                        onError = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = it,
                                    style = typography.titleLarge.copy(color = Color.Red)
                                )
                            }
                        }
                    )
                    deleteNoteState.value.DisplayResult(
                        onLoading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(64.dp),
                                    strokeWidth = 12.dp
                                )
                            }
                        },
                        onSuccess = {
                            Toasty.error(context, "Note delete successfully", Toasty.LENGTH_LONG, true)
                                .show()
                            goBack()
                        },
                        onError = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = it,
                                    style = typography.titleLarge.copy(color = Color.Red)
                                )
                            }
                        }
                    )
                }
            },
            onError = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = it,
                        style = typography.titleLarge.copy(color = Color.Red)
                    )
                }
            }
        )
    }
}