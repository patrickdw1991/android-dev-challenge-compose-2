/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    var timerJob: Job? = null

    val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme {
                val timeLeft: Int by viewModel.timeLeft.observeAsState(60)
                val totalTime: Int by viewModel.totalTime.observeAsState(60)
                val state: MainViewModel.State by viewModel.state.observeAsState(MainViewModel.State.STOPPED)

                TimerScreen(timeLeft, totalTime, state)
            }
        }
    }

    // Start building your app here!
    @Composable
    fun TimerScreen(
        timeLeft: Int,
        totalTime: Int,
        state: MainViewModel.State
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    ProgressView(
                        timeLeft, totalTime,
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                    TimerView(
                        timeLeftInSeconds = timeLeft,
                        isEditable = state != MainViewModel.State.RUNNING,
                        onMinutePlus = { viewModel.addMinute() },
                        onMinuteMinus = { viewModel.removeMinute() },
                        onSecondPlus = { viewModel.addSecond() },
                        onSecondMinus = { viewModel.removeSecond() }
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val canStart = state != MainViewModel.State.RUNNING
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = { startTimer() },
                        enabled = totalTime != 0 && canStart
                    ) {
                        Text(text = "Start timer")
                    }
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = { stopTimer() },
                        enabled = !canStart
                    ) {
                        Text(text = "Stop timer")
                    }
                }
            }
        }
    }

    private fun startTimer() {
        stopTimer()

        viewModel.setStateRunning()
        timerJob = lifecycleScope.launchWhenResumed {
            delay(1000)
            repeat(viewModel.totalTime.value ?: 0) {
                viewModel.decreaseTime()
                delay(1000)
            }

            stopTimer()
        }
    }

    private fun stopTimer() {
        viewModel.setStateStopped()
        timerJob?.cancel()
        timerJob = null
    }

    @Preview("Light Theme", widthDp = 360, heightDp = 640)
    @Composable
    fun LightPreview() {
        MyTheme {
            TimerScreen(10, 100, MainViewModel.State.STOPPED)
        }
    }

    @Preview("Dark Theme", widthDp = 360, heightDp = 640)
    @Composable
    fun DarkPreview() {
        MyTheme(darkTheme = true) {
            TimerScreen(10, 100, MainViewModel.State.STOPPED)
        }
    }
}
