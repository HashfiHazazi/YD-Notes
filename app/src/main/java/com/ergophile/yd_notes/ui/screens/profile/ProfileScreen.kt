package com.ergophile.yd_notes.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chibatching.kotpref.Kotpref
import com.ergophile.yd_notes.KotprefLocalStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit
) {
    val redButtonColor = 0XFFCC0000
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = modifier.padding(horizontal = 16.dp),
                title = {
                    Text(
                        text = "Profile",
                        fontWeight = FontWeight.SemiBold,
                        color = colorScheme.primary
                    )
                },
                navigationIcon = {
                    Card(
                        modifier = modifier.size(48.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        border = BorderStroke(1.5.dp, color = colorScheme.primary),
                        onClick = {
                            goBack()
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
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = modifier.size(128.dp),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon"
            )
            Text(
                text = KotprefLocalStorage.username,
                style = typography.headlineMedium.copy(fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = modifier.height(24.dp))
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Email:", style = typography.titleMedium)
                Text(text = KotprefLocalStorage.email, style = typography.titleLarge)
            }
            Spacer(modifier = modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 64.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(redButtonColor)),
                onClick = {
                    KotprefLocalStorage.clear()
                }) {
                Text(text = "Logout", fontWeight = FontWeight.Bold)
            }
        }
    }
}