package com.example.bunpolite.ui.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bunpolite.R
import com.example.bunpolite.ui.shared.BackNavigationIcon
import com.example.bunpolite.ui.shared.MainTopAppBar
import com.example.bunpolite.ui.shared.ShowSnackbar
import kotlinx.serialization.Serializable

@Serializable
object AuthRoute

@Composable
fun AuthScreen(
    goBack: () -> Unit,
    showSnackbar: ShowSnackbar,
    viewModel: AuthViewModel = hiltViewModel<AuthViewModel>()
) {
    val shouldRestartApp by viewModel.shouldRestartApp.collectAsStateWithLifecycle()

    if (shouldRestartApp) {
        goBack()
    } else {
        AuthScreenContent(
            goBack = goBack,
            onClickSignIn = viewModel::signIn,
            onClickSignUp = viewModel::signUp,
            showSnackbar = showSnackbar,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun AuthScreenContent(
    goBack: () -> Unit,
    onClickSignIn: (ShowSnackbar, String, String) -> Unit,
    onClickSignUp: (ShowSnackbar, String, String) -> Unit,
    showSnackbar: ShowSnackbar,
    modifier: Modifier = Modifier
) {
    var isSignInContent by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopAppBar(
                titleResId = R.string.authentication,
                currentNavigationIcon = { BackNavigationIcon(onClickGoBack = goBack) }
            )
        }
    ) { innerPadding ->
        val mainModifier = Modifier.fillMaxSize().padding(innerPadding)
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
            .verticalScroll(rememberScrollState())

        AnimatedContent(
            targetState = isSignInContent,
            transitionSpec = {
                slideInHorizontally { width -> width } + fadeIn() togetherWith
                        slideOutHorizontally { width -> -width } + fadeOut()
            }
        ) { targetCondition ->
            if (targetCondition) {
                SignInContent(
                    toSignUpContent = { isSignInContent = false },
                    onClickSignIn = onClickSignIn,
                    showSnackbar = showSnackbar,
                    modifier = mainModifier
                )
            } else {
                SignUpContent(
                    toSignInContent = { isSignInContent = true },
                    onClickSignUp = onClickSignUp,
                    showSnackbar = showSnackbar,
                    modifier = mainModifier
                )
            }
        }
    }
}

@Composable
private fun SignInContent(
    toSignUpContent: () -> Unit,
    onClickSignIn: (ShowSnackbar, String, String) -> Unit,
    showSnackbar: ShowSnackbar,
    modifier: Modifier = Modifier
) {
    val email = rememberTextFieldState()
    val password = rememberTextFieldState()
    var passwordHidden by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_medium)
            )
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )

            OutlinedTextField(
                state = email,
                label = { Text(text = stringResource(id = R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                )
            )

            OutlinedSecureTextField(
                state = password,
                label = { Text(text = stringResource(id = R.string.password)) },
                textObfuscationMode = if (passwordHidden) TextObfuscationMode.RevealLastTyped
                else TextObfuscationMode.Visible,
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        Icon(
                            imageVector = if (passwordHidden) Icons.Outlined.Lock
                            else Icons.Filled.Lock,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                )
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.padding_small)))

            Button(
                onClick = {
                    onClickSignIn(showSnackbar, email.text.toString(), password.text.toString())
                },
                enabled = email.text.isNotBlank() && password.text.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.sign_in)) }

            OutlinedButton(
                onClick = toSignUpContent,
                modifier = Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.go_to_sign_up)) }
        }
    }
}

@Composable
private fun SignUpContent(
    toSignInContent: () -> Unit,
    onClickSignUp: (ShowSnackbar, String, String) -> Unit,
    showSnackbar: ShowSnackbar,
    modifier: Modifier = Modifier
) {
    val email = rememberTextFieldState()
    val password = rememberTextFieldState()
    val passwordConfirm = rememberTextFieldState()

    var passwordHidden by remember { mutableStateOf(true) }
    var passwordConfirmHidden by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_medium)
            )
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )

            OutlinedTextField(
                state = email,
                label = { Text(text = stringResource(id = R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                )
            )

            OutlinedSecureTextField(
                state = password,
                label = { Text(text = stringResource(id = R.string.password)) },
                textObfuscationMode = if (passwordHidden) TextObfuscationMode.RevealLastTyped
                else TextObfuscationMode.Visible,
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        Icon(
                            imageVector = if (passwordHidden) Icons.Outlined.Lock
                            else Icons.Filled.Lock,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                )
            )

            OutlinedSecureTextField(
                state = passwordConfirm,
                label = { Text(text = stringResource(id = R.string.confirm_password)) },
                textObfuscationMode = if (passwordConfirmHidden) TextObfuscationMode.RevealLastTyped
                else TextObfuscationMode.Visible,
                trailingIcon = {
                    IconButton(onClick = { passwordConfirmHidden = !passwordConfirmHidden }) {
                        Icon(
                            imageVector = if (passwordConfirmHidden) Icons.Outlined.Lock
                            else Icons.Filled.Lock,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                )
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.padding_small)))

            Button(
                onClick = {
                    onClickSignUp(showSnackbar,email.text.toString(), password.text.toString())
                },
                enabled = email.text.isNotBlank() && password.text.isNotBlank() &&
                        passwordConfirm.text == password.text,
                modifier = Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.sign_up)) }

            OutlinedButton(
                onClick = toSignInContent,
                modifier = Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.go_to_sign_in)) }
        }
    }
}