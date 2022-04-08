package com.wtfcompany.relax.service

import android.app.Service
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.wtfcompany.relax.view.home.data.Mood

class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private val binder: IBinder = LocalBinder()
    private lateinit var music: Map<Mood, List<AssetFileDescriptor>>
    private lateinit var currentList: List<AssetFileDescriptor>
    private var currentMusic = 0
    private var isRepeat = false

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        music = mapOf(
            Mood.CALM to loadListMusic(Mood.CALM.name),
            Mood.RELAX to loadListMusic(Mood.RELAX.name),
            Mood.FOCUS to loadListMusic(Mood.FOCUS.name),
            Mood.EXCITED to loadListMusic(Mood.EXCITED.name),
            Mood.FUN to loadListMusic(Mood.FUN.name),
            Mood.SADNESS to loadListMusic(Mood.SADNESS.name),
        )
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener { next() }
    }

    fun prev() {
        currentMusic = (currentList.size - 1 + currentMusic) % currentList.size
        play()
    }

    fun next() {
        currentMusic = (currentMusic + 1) % currentList.size
        play()
    }

    fun shuffle() {
        currentList = currentList.shuffled()
    }

    fun repeat() {
        isRepeat = !isRepeat
        if (isRepeat)
            mediaPlayer.setOnCompletionListener { play() }
        else mediaPlayer.setOnCompletionListener { next() }

    }

    fun playPause() {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        else play()
    }

    private fun play() {
        val file = currentList[currentMusic]
        mediaPlayer.reset()
        mediaPlayer.setDataSource(file.fileDescriptor, file.startOffset, file.length)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    private fun loadListMusic(mood: String) = listOf(
        assets.openFd("music/$mood/1.mp3"),
        assets.openFd("music/$mood/2.mp3"),
        assets.openFd("music/$mood/3.mp3"),
        assets.openFd("music/$mood/4.mp3"),
        assets.openFd("music/$mood/5.mp3")
    )

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.extras != null) {
            println("extra " + intent.extras?.get("mood"))
            when (intent.extras?.get("mood")) {
                Mood.CALM.name -> currentList = music[Mood.CALM]!!
                Mood.RELAX.name -> currentList = music[Mood.RELAX]!!
                Mood.FOCUS.name -> currentList = music[Mood.FOCUS]!!
                Mood.EXCITED.name -> currentList = music[Mood.EXCITED]!!
                Mood.FUN.name -> currentList = music[Mood.FUN]!!
                Mood.SADNESS.name -> currentList = music[Mood.SADNESS]!!
                "none" -> currentList = music[Mood.FUN]!!
                else -> stopSelf()
            }
        }
        return START_STICKY
    }

    inner class LocalBinder : Binder() {
        val service: MusicService
            get() = this@MusicService
    }
}