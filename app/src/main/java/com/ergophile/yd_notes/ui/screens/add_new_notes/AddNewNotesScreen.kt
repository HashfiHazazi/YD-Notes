package com.ergophile.yd_notes.ui.screens.add_notes

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergophile.yd_notes.KotprefLocalStorage
import com.ergophile.yd_notes.R
import com.ergophile.yd_notes.YDNotesApplication
import com.ergophile.yd_notes.helper.viewModelFactoryHelper
import com.ergophile.yd_notes.ui.screens.add_new_notes.AddNewNotesViewModel
import es.dmoral.toasty.Toasty
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewNotesScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    viewModel: AddNewNotesViewModel = viewModel(
        factory = viewModelFactoryHelper {
            AddNewNotesViewModel(YDNotesApplication.appModule.getRepository)
        }
    )
) {

    var showSaveNoteDialog by remember {
        mutableStateOf(false)
    }

    var titleNotesValue by remember {
        mutableStateOf("")
    }

    var subtitleNotesValue by remember {
        mutableStateOf("")
    }

    var contentNotesValue by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val addNewNoteState = viewModel.addNewNotesState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = modifier.padding(horizontal = 16.dp),
                title = {
                    Text(
                        text = "Add New Note",
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    Card(
                        modifier = modifier.size(48.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        border = BorderStroke(1.5.dp, color = MaterialTheme.colorScheme.primary),
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
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        ) {
            Column {
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
                        onDismissRequest = {},
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
                                    viewModel.addNewNotes(
                                        noteBody = buildJsonObject {
                                            put("user_uuid_notes", KotprefLocalStorage.userUid)
                                            put("title_notes", titleNotesValue)
                                            put("subtitle_notes", subtitleNotesValue)
                                            put("notes_content", contentNotesValue)
                                            put("modified_at", "now()")
                                        }
                                    )
                                    goBack()
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
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = modifier.padding(horizontal = 16.dp),
                    text = "Notes title:",
                    style = MaterialTheme.typography.titleMedium
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
                    textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),

                    )
                Spacer(modifier = modifier.height(24.dp))
                Text(
                    modifier = modifier.padding(horizontal = 16.dp),
                    text = "Notes subtitle:",
                    style = MaterialTheme.typography.titleMedium
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
                    textStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),

                    )
                Spacer(modifier = modifier.height(24.dp))
                Text(
                    modifier = modifier.padding(horizontal = 16.dp),
                    text = "Notes content:",
                    style = MaterialTheme.typography.titleMedium
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
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                )
            }
            addNewNoteState.value.DisplayResult(
                onLoading = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                            strokeWidth = 12.dp
                        )
                    }
                },
                onSuccess = {
                    showSaveNoteDialog = false
                    Toasty.success(context, "Note Added", Toasty.LENGTH_SHORT, true).show()
                },
                onError = {
                    Toasty.error(context, it, Toasty.LENGTH_SHORT, true).show()
                }
            )
        }
    }

}