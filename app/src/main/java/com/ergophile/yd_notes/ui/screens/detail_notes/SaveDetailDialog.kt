package com.ergophile.yd_notes.ui.screens.detail_notes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ergophile.yd_notes.R

@Composable
fun SaveDetailDialog(
    modifier: Modifier = Modifier,
    showDialogState: Boolean
) {
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
            Text(text = "Save note changes?", fontWeight = FontWeight.SemiBold)
        },
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(
                onClick = {

                }
            ) {
                Text("save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {

                }
            ) {
                Text("cancel")
            }
            TextButton(
                onClick = {
//                    showDialogState = false
                }
            ) {
                Text("don't save")
            }
        }
    )
}