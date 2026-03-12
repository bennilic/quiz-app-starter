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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiz_app_starter.model.Question
import com.example.quiz_app_starter.model.getDummyQuestions
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    questions: List<Question> = getDummyQuestions(),
    onNavigateToFinish: (Int) -> Unit = {}
) {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    val question = questions[currentQuestionIndex]

    var selectedAnswer by remember { mutableStateOf<String?>(null) }

    val timerDurationSeconds = 30
    var timeLeft by remember { mutableIntStateOf(timerDurationSeconds) }
    var timerRunning by remember { mutableStateOf(true) }

    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(currentQuestionIndex) {
        timeLeft = timerDurationSeconds
        timerRunning = true
        selectedAnswer = null
        while (timeLeft > 0 && timerRunning) {
            delay(1000L)
            timeLeft--
        }
        if (timerRunning && timeLeft == 0) {
            if (selectedAnswer == null) {
                dialogTitle = "No answer selected.\nTime is out."
                dialogMessage = "The correct answer was: ${question.correctAnswer}"
            } else if (selectedAnswer == question.correctAnswer) {
                dialogTitle = "Correct!"
                dialogMessage = null
                score++
            } else {
                dialogTitle = "Wrong!"
                dialogMessage = "The correct answer was: ${question.correctAnswer}"
            }
            timerRunning = false
            showDialog = true
        }
    }

    fun onSubmit() {
        timerRunning = false
        if (selectedAnswer == null) {
            dialogTitle = "No answer selected.\nTime is out."
            dialogMessage = "The correct answer was: ${question.correctAnswer}"
        } else if (selectedAnswer == question.correctAnswer) {
            dialogTitle = "Correct!"
            dialogMessage = null
            score++
        } else {
            dialogTitle = "Wrong!"
            dialogMessage = "The correct answer was: ${question.correctAnswer}"
        }
        showDialog = true
    }

    fun onNext() {
        showDialog = false
        if (currentQuestionIndex + 1 < questions.size) {
            currentQuestionIndex++
        } else {
            onNavigateToFinish(score)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(dialogTitle) },
            text = dialogMessage?.let { { Text(it) } },
            confirmButton = {
                Button(onClick = { onNext() }) {
                    Text("Next")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz App") },
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
                    onClick = { onSubmit() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Submit")
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                LinearProgressIndicator(
                    progress = { timeLeft.toFloat() / timerDurationSeconds.toFloat() },
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
                        text = "Q${currentQuestionIndex + 1}: ${question.question}",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            items(question.answers) { answer ->
                AnswerCard(
                    answer = answer,
                    isSelected = answer == selectedAnswer,
                    onSelect = { selectedAnswer = answer }
                )
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

@Preview(showBackground = true)
@Composable
fun QuestionScreenPreview() {
    QuestionScreen()
}
