package com.example.bunpolite.ui.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bunpolite.R
import com.example.bunpolite.data.model.MainLesson
import com.example.bunpolite.ui.shared.MainTopAppBar
import com.example.bunpolite.ui.shared.NeedRefreshListScreen
import com.example.bunpolite.ui.shared.ShowSnackbar
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

private const val MAX_LINE_CARD_CONTENT = 1
private const val PLACEHOLDER_SWAP_TIME = 2000L
@Serializable
object MainLessonRoute

@Composable
fun MainLessonScreen(
    openLessonScreen: (String) -> Unit,
    showSnackbar: ShowSnackbar,
    viewModel: MainLessonViewModel = hiltViewModel<MainLessonViewModel>()
) {
    val context = LocalContext.current
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler { showExitDialog = true }

    if (showExitDialog) {
        ExitAppAlertDialog(
            onDismiss = { showExitDialog = false },
            onConfirm = {
                showExitDialog = false
                (context as? Activity)?.finish()
            }
        )
    }
}

@Composable
private fun MainLessonScreenContent(
    openLessonScreen: (String) -> Unit,
    showSnackbar: ShowSnackbar,
    onToggleLanguageView: (ShowSnackbar) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    uiState: MainLessonUiState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { MainTopAppBar(
            titleResId = R.string.main,
            currentActions = {
                val buttonLabelResId = if (uiState.isMainLessonEnView) R.string.jp_view else R.string.en_view
                FilledTonalButton(
                    onClick = { onToggleLanguageView(showSnackbar) },
                    enabled = !uiState.displayRefreshButton
                ) { Text(stringResource(buttonLabelResId)) }
            }
        ) }
    ) { innerPadding ->
        val parentModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        if (uiState.displayRefreshButton) {
            NeedRefreshListScreen(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                modifier = parentModifier
            )
        } else {
            LessonLazyList(
                uiState = uiState,
                openLessonScreen = openLessonScreen,
                modifier = parentModifier
            )
        }
    }
}

@Composable
private fun FilterAndConfigureList(
    currentSearchType: MainLessonFilterSearch,
    onChangeSearchType: (MainLessonFilterSearch) -> Unit,
    onClickSearch: (String) -> Unit,
    onClickGoToLesson: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        SearchInputRow(
            currentSearchType = currentSearchType,
            onChangeSearchType = onChangeSearchType,
            onClickSearch = onClickSearch,
            modifier = Modifier.weight(2f)
                .widthIn(
                    min = dimensionResource(R.dimen.filter_text_input_row_min_width),
                    max = dimensionResource(R.dimen.filter_text_input_row_max_width)
                )
        )

        GoToLessonRow(
            onClickGoToLesson = onClickGoToLesson,
            modifier = Modifier.weight(1f)
                .widthIn(
                    min = dimensionResource(R.dimen.go_to_lesson_input_row_min_width),
                    max = dimensionResource(R.dimen.go_to_lesson_input_row_max_width)
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchInputRow(
    currentSearchType: MainLessonFilterSearch,
    onChangeSearchType: (MainLessonFilterSearch) -> Unit,
    onClickSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val placeholders = currentSearchType.exampleResId
    var currentIndex by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(PLACEHOLDER_SWAP_TIME)
            currentIndex = (currentIndex + 1) % placeholders.size
        }
    }

    val searchText = rememberTextFieldState()
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small))
    ) {
        TextField(
            state = searchText,
            lineLimits = TextFieldLineLimits.SingleLine,
            placeholder = {
                AnimatedContent(
                    targetState = placeholders[currentIndex],
                    transitionSpec = { // Slide in from bottom, slide out to top
                        (slideInVertically { height -> height } + fadeIn())
                            .togetherWith(slideOutVertically { height -> -height } + fadeOut())
                    }
                ) { targetPlaceholderResId ->
                    Text(text = stringResource(targetPlaceholderResId), color = Color.Gray)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            modifier = Modifier.weight(1f)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.weight(2f)
        ) {
            TextField(
                value = stringResource(currentSearchType.labelResId),
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                readOnly = true,
                singleLine = true,
                modifier = Modifier.widthIn(
                    min = dimensionResource(R.dimen.choose_type_search_min_width),
                    max = dimensionResource(R.dimen.choose_type_search_max_width)
                ).menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                MainLessonFilterSearch.entries.forEach { searchType ->
                    DropdownMenuItem(
                        text = { Text(stringResource(currentSearchType.labelResId)) },
                        onClick = {
                            onChangeSearchType(searchType)
                        }
                    )
                }
            }
        }

        IconButton(onClick = { onClickSearch(searchText.text.toString()) }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search)
            )
        }
    }
}

@Composable
private fun GoToLessonRow(
    onClickGoToLesson: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val goToNumberText = rememberTextFieldState()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small))
    ) {
        TextField(
            state = goToNumberText,
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Go,
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.weight(1f)
        )

        OutlinedButton(onClick = { onClickGoToLesson(goToNumberText.text.toString()) }) {
            Text(stringResource(R.string.go_to_lesson))
        }
    }
}

@Composable
private fun LessonLazyList(
    uiState: MainLessonUiState,
    openLessonScreen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(dimensionResource(R.dimen.lesson_card_min_width)),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small)),
    ) {
        items(
            items = uiState.mainLessons,
            key = { lesson -> lesson.id },
            contentType = { MainLesson::class }
        ) { lesson ->
            LessonCard(
                isOnEnView = uiState.isMainLessonEnView,
                lesson = lesson,
                onClickLessonCard = openLessonScreen
            )
        }
    }
}

@Composable
private fun LessonCard(
    isOnEnView: Boolean,
    lesson: MainLesson,
    onClickLessonCard: (String) -> Unit
) {
    Card(
        onClick = { onClickLessonCard(lesson.id) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.width(dimensionResource(R.dimen.lesson_card_index_indicator_width)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = lesson.index.toString(),
                    style = MaterialTheme.typography.displaySmall
                )
            }

            if (isOnEnView) {
                LessonCardContentEn(lesson)
            } else {
                LessonCardContentJp(lesson)
            }
        }
    }
}

@Composable
private fun LessonCardContentEn(lesson: MainLesson) {
    ListItem(
        overlineContent = { Text(
            text = lesson.categoryEn,
            maxLines = MAX_LINE_CARD_CONTENT,
            overflow = TextOverflow.Ellipsis
        ) },
        headlineContent = { Text(
            text = lesson.title,
            maxLines = MAX_LINE_CARD_CONTENT,
            overflow = TextOverflow.Ellipsis
        ) },
        supportingContent = { Text(
            text = lesson.sentencePatternEn,
            maxLines = MAX_LINE_CARD_CONTENT,
            overflow = TextOverflow.Ellipsis
        ) },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
            headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
            supportingColor = MaterialTheme.colorScheme.primary,
            overlineColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun LessonCardContentJp(lesson: MainLesson) {
    ListItem(
        overlineContent = { Text(
            text = lesson.categoryJp,
            maxLines = MAX_LINE_CARD_CONTENT,
            overflow = TextOverflow.Ellipsis
        ) },
        headlineContent = { Text(
            text = lesson.title,
            maxLines = MAX_LINE_CARD_CONTENT,
            overflow = TextOverflow.Ellipsis
        ) },
        supportingContent = { Text(
            text = lesson.sentencePatternJp,
            maxLines = MAX_LINE_CARD_CONTENT,
            overflow = TextOverflow.Ellipsis
        ) },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
            headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
            supportingColor = MaterialTheme.colorScheme.primary,
            overlineColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ExitAppAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.alert_dialog_exit_app_title)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.alert_dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.alert_dialog_cancel))
            }
        }
    )
}