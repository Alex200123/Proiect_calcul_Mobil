package com.example.lunchtray.Backend

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.IllegalStateException


public class Audio {

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    @SuppressLint("RestrictedApi")
    public fun startRecording(file: File) {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(FileOutputStream(file).fd)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
                println(""+e);
            }

            try {
                start()
            }catch (e : IllegalStateException){
                println(""+e);
            }

        }
    }

    public fun stopRecording() {
        recorder?.apply {
            stop()
            reset()
        }
        recorder = null
    }

    @SuppressLint("RestrictedApi")
    public fun startPlaying(context: Context, file: File) {
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
        }

    }

    public fun stopPlaying() {
        player?.stop()
        player?.release()
        player = null
    }


}