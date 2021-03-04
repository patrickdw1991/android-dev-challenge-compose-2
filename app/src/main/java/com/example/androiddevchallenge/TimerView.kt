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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun TimerView(
    timeLeftInSeconds: Int,
    isEditable: Boolean,
    onMinutePlus: () -> Unit = { },
    onMinuteMinus: () -> Unit = { },
    onSecondPlus: () -> Unit = { },
    onSecondMinus: () -> Unit = { }
) {
    val minutesLeft = timeLeftInSeconds / 60
    val restSeconds = timeLeftInSeconds % 60

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onMinutePlus, enabled = isEditable) {
                Text(text = "+")
            }
            Text(
                text = "$minutesLeft".padStart(2, '0'),
                modifier = Modifier.padding(4.dp)
            )
            Button(onClick = onMinuteMinus, enabled = isEditable) {
                Text(text = "-")
            }
        }

        Text(text = ":")

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onSecondPlus, enabled = isEditable) {
                Text(text = "+")
            }
            Text(
                text = "$restSeconds".padStart(2, '0'),
                modifier = Modifier.padding(4.dp)
            )
            Button(onClick = onSecondMinus, enabled = isEditable) {
                Text(text = "-")
            }
        }
    }
}

@Preview("timer preview", widthDp = 360, heightDp = 640)
@Composable
fun TimerPreview() {
    MyTheme {
        TimerView(60, true)
    }
}
