package com.example.todoapp.utils

import android.content.Context
import android.media.MediaPlayer

object SoundPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun playSound(context: Context, soundResourceId: Int) {
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(context, soundResourceId)

        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
        }

        mediaPlayer?.start()
    }
}