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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun ProgressView(
    timeLeft: Int,
    totalTime: Int,
    modifier: Modifier = Modifier
) {
    if (totalTime > 0) {
        val animatedProgress = animateFloatAsState(
            targetValue = timeLeft.toFloat() / totalTime.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        ).value
        CircularProgressIndicator(progress = animatedProgress, modifier)
    } else {
        CircularProgressIndicator(progress = 1f, modifier)
    }
}

@Preview("progress preview 100%", widthDp = 360, heightDp = 640)
@Composable
fun ProgressPreview100Percent() {
    MyTheme {
        ProgressView(10, 100)
    }
}

@Preview("progress preview 80%", widthDp = 360, heightDp = 640)
@Composable
fun ProgressPreview80Percent() {
    MyTheme {
        ProgressView(80, 100)
    }
}
