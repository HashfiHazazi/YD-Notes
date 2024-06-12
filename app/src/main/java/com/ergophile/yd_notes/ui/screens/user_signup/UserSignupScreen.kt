package com.ergophile.yd_notes.ui.screens.user_signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergophile.yd_notes.KotprefLocalStorage
import com.ergophile.yd_notes.R
import com.ergophile.yd_notes.YDNotesApplication
import com.ergophile.yd_notes.helper.viewModelFactoryHelper
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Composable
fun UserSignupScreen(
    modifier: Modifier = Modifier,
    goToLogin: () -> Unit,
    goToHome: () -> Unit,
    viewModel: UserSignupViewModel = viewModel(factory = viewModelFactoryHelper {
        UserSignupViewModel(YDNotesApplication.appModule.getRepository)
    })
) {
    val userSignUpState = viewModel.userSignUpState.collectAsState()

    var usernameValue by remember {
        mutableStateOf("")
    }

    var emailValue by remember {
        mutableStateOf("")
    }

    var passwordValue by remember {
        mutableStateOf("")
    }

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    val gradientColor = listOf(colorScheme.primary, colorScheme.secondary)
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxSize()){
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Image(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 38.dp),
                painter = painterResource(id = R.drawable.ydnotes_logo),
                contentDescription = "YD-Notes Logo"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Sign Up",
                style = typography.headlineMedium.copy(
                    brush = Brush.linearGradient(
                        colors = gradientColor,
                        tileMode = TileMode.Mirror
                    ),
                ),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                modifier = modifier.padding(start = 16.dp, bottom = 4.dp),
                text = "Username",
                style = typography.bodyMedium.copy(color = colorScheme.primary)
            )
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = usernameValue,
                onValueChange = {
                    usernameValue = it
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = colorScheme.primary,
                    focusedBorderColor = colorScheme.primary
                ),
                shape = RoundedCornerShape(8.dp),
                maxLines = 1
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                modifier = modifier.padding(start = 16.dp, bottom = 4.dp),
                text = "Email",
                style = typography.bodyMedium.copy(color = colorScheme.primary)
            )
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = emailValue,
                onValueChange = {
                    emailValue = it
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = colorScheme.primary,
                    focusedBorderColor = colorScheme.primary
                ),
                shape = RoundedCornerShape(8.dp),
                maxLines = 1,
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                modifier = modifier.padding(start = 16.dp, bottom = 4.dp),
                text = "Password",
                style = typography.bodyMedium.copy(color = colorScheme.primary)
            )
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = passwordValue,
                onValueChange = {
                    passwordValue = it
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = colorScheme.primary,
                    focusedBorderColor = colorScheme.primary
                ),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                maxLines = 1,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisibility) R.drawable.outline_visibility_24 else R.drawable.outline_visibility_off_24),
                            contentDescription = "Visibility Button"
                        )
                    }
                }
            )
            Spacer(modifier = modifier.height(16.dp))
            TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
                goToLogin()
            }) {
                Text(
                    text = "Do you have Account? Login",
                    style = typography.bodyMedium.copy(color = Color(0XFF3483eb))
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    viewModel.postUserSignUp(
                        buildJsonObject {
                            put("email", emailValue)
                            put("password", passwordValue)
                            put("data", buildJsonObject {
                                put("username", usernameValue)
                            })
                        }
                    )
                }) {
                Text(text = "Signup", fontWeight = FontWeight.Bold)
            }
        }
        userSignUpState.value.DisplayResult(
            modifier = modifier.fillMaxSize(),
            onLoading = {
                Box(modifier = modifier.fillMaxSize().background(color = Color.Black.copy(alpha = 0.6f)), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = modifier.size(64.dp),
                        strokeWidth = 14.dp
                    )
                }
            }, onSuccess = {
                goToHome()
                KotprefLocalStorage.accessToken = it.accessToken
                KotprefLocalStorage.email = it.user.userMetadata.email
                KotprefLocalStorage.username = it.user.userMetadata.username
                KotprefLocalStorage.userUid = it.user.id
            }, onError = {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}