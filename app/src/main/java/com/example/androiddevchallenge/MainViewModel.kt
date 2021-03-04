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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _timeLeft = MutableLiveData(60)
    private val _totalTime = MutableLiveData(60)
    private val _state = MutableLiveData(State.STOPPED)
    val timeLeft: LiveData<Int> = _timeLeft
    val totalTime: LiveData<Int> = _totalTime
    val state: LiveData<State> = _state

    fun decreaseTime() {
        _timeLeft.value = _timeLeft.value?.minus(1)
    }

    fun addMinute() {
        _totalTime.value = _totalTime.value?.plus(60)
        _timeLeft.value = _timeLeft.value?.plus(60)
    }

    fun addSecond() {
        _totalTime.value = _totalTime.value?.plus(1)
        _timeLeft.value = _timeLeft.value?.plus(1)
    }

    fun removeMinute() {
        _totalTime.value = _totalTime.value?.minus(60)
        _timeLeft.value = _timeLeft.value?.minus(60)
        adjustToZeroIfNeeded()
    }

    fun removeSecond() {
        _totalTime.value = _totalTime.value?.minus(1)
        _timeLeft.value = _timeLeft.value?.minus(1)
        adjustToZeroIfNeeded()
    }

    fun adjustToZeroIfNeeded() {
        if ((_totalTime.value ?: 0) < 0) {
            _totalTime.value = 0
        }
        if ((_timeLeft.value ?: 0) < 0) {
            _timeLeft.value = 0
        }
    }

    fun setStateRunning() {
        _state.value = State.RUNNING
    }

    fun setStateStopped() {
        _state.value = State.STOPPED
        _timeLeft.value = _totalTime.value
    }

    enum class State {
        RUNNING,
        STOPPED
    }
}
