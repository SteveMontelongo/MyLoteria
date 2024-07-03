package com.example.myloteria.util

import android.util.Log
import kotlinx.coroutines.*

class Timer(private val onTick: () -> Unit) {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    private var timer: Job? =null

    private fun startCoroutineTimer(delayMillis: Long = 0, repeatMillis: Long = 0, action: ()-> Unit ) = scope.launch(Dispatchers.IO){
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }

    }

//    private val timer: Job = startCoroutineTimer(delayMillis = 0, repeatMillis = 2000) {
//        Log.d("Timer", "Background - tick")
//        //doSomethingBackground()
//        scope.launch(Dispatchers.Main) {
//            Log.d("Timer", "Main thread - tick")
//            //doSomethingMainThread()
//            onTick()
//
//        }
//    }

    fun startTimer() {
        Log.d("Timer", "Timer started.")
        timer = startCoroutineTimer(delayMillis = 0, repeatMillis = 5000) {
            Log.d("Timer", "Background - tick")
            // Do something in the background
            scope.launch(Dispatchers.Main) {
                Log.d("Timer", "Main thread - tick")
                // Do something on the main thread
                onTick()
            }
        }
    }

    fun cancelTimer() {
        Log.d("Timer", "Timer cancelled.")
        timer?.cancel()
    }
}