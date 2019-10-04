package com.example.foldableapps

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer.OnCompletionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "TAG"
    private val VIDEO_STATE = "VIDEO_STATE"

    private var mCurrentPosition = -1
    var mCurrentPath: Int = 0

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val videoView = findViewById<VideoView>(R.id.videoView)
        if(savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(VIDEO_STATE)
        }

        val path = "android.resource://" + packageName + "/" + R.raw.a
        videoView?.setVideoURI(Uri.parse(path))

        val button = findViewById<Button>(R.id.button)
        button?.setOnClickListener {
            val isPlaying = videoView.isPlaying
            button.setText(if (isPlaying) R.string.play else R.string.pause)

            val msg = getString(if (isPlaying) R.string.paused else R.string.playing)
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            if (isPlaying) {
                videoView.pause()
            } else {
                videoView.start()
            }
        }

    }

    override fun onStart() {
        play()
        videoView.setOnCompletionListener(OnCompletionListener { play() })
        super.onStart()
    }

    private fun play() {

        videoView.stopPlayback()
        videoView.requestFocus()
        videoView.start()
            if (mCurrentPosition != -1) {
                videoView.seekTo(mCurrentPosition)
            }
            count++
    }

    override fun onPause() {
        mCurrentPosition = videoView.currentPosition
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(VIDEO_STATE, mCurrentPath)
        outState.putInt(VIDEO_STATE, mCurrentPosition)
        super.onSaveInstanceState(outState)
    }

}
