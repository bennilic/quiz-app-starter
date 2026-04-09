package com.example.quiz_app_starter.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    viewModel: QuestionScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(viewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(viewModel)
        }
    }

    val question = uiState.currentQuestion

    if (uiState.showResultDialog) {
        AlertDialog(
            onDismissRequest = viewModel::onDismissDialog,
            title = { Text(if (uiState.dialogMessage.startsWith("Correct")) "Correct!" else "Wrong!") },
            text = { Text(uiState.dialogMessage) },
            confirmButton = {
                Button(onClick = viewModel::onDismissDialog) {
                    val isLast = uiState.currentQuestionIndex + 1 >= uiState.questions.size
                    Text(if (isLast) "Finish" else "Next")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz App — ${uiState.pointsAchieved} pts") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = viewModel::onSubmit,
                    enabled = uiState.selectedAnswer != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Submit")
                }
            }
        }
    ) { innerPadding ->
        if (question != null) {
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    LinearProgressIndicator(
                        progress = { uiState.timerProgress },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Q${uiState.currentQuestionIndex + 1}: ${question.question}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                items(question.answers) { answer ->
                    AnswerCard(
                        answer = answer,
                        isSelected = answer == uiState.selectedAnswer,
                        onSelect = { viewModel.onAnswerSelected(answer) }
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerCard(
    answer: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = answer, modifier = Modifier.weight(1f))
            RadioButton(
                selected = isSelected,
                onClick = onSelect
            )
        }
    }
}

