package com.baorun.handbook.a58.feature.video

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Bundle
import android.util.PlatformUtil
import android.util.ScreenSaverStateListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.baorun.handbook.a58.KEY_BUNDLE_BELONG
import com.baorun.handbook.a58.KEY_BUNDLE_ID
import com.baorun.handbook.a58.KEY_BUNDLE_PATH
import com.baorun.handbook.a58.databinding.ActivityPlayerBinding
import com.baorun.handbook.a58.ext.goActivity
import com.baorun.handbook.a58.feature.collect.CollectionViewModel
import com.baorun.handbook.a58.feature.search.SearchActivity
import com.baorun.handbook.a58.widget.JZMediaSystemAssertFolder

/**
 * 功能：
 * 描述：视频播放页
 * Created by xukun on 2021/8/19.
 */
open abstract class  PlayerActivity : AppCompatActivity() {

    protected lateinit var viewBinding: ActivityPlayerBinding
    private lateinit var am: AudioManager
    private lateinit var audioFocus:AudioFocusRequest
    private var screenSaveStateListener: ScreenSaverStateListener = object :ScreenSaverStateListener{
        override fun onScreenSaverStateChanged(show: Boolean) {
          runOnUiThread {
              if(show){
                  Jzvd.goOnPlayOnPause()
              }else{
                  Jzvd.goOnPlayOnResume()
              }
          }

        }

        override fun onScreenChanged(show: Boolean) {
            runOnUiThread{
                if(show){
                    Jzvd.goOnPlayOnResume()
                }else{
                    Jzvd.goOnPlayOnPause()
                }
            }

        }

    }

    private val mCollectViewModel by viewModels<CollectionViewModel>()

    protected val path: String by lazy {
        intent.getStringExtra(KEY_BUNDLE_PATH) ?: ""
    }

    protected val id: String by lazy {
        intent.getStringExtra(KEY_BUNDLE_ID) ?: ""
    }

    protected val belong: String by lazy {
        intent.getStringExtra(KEY_BUNDLE_BELONG) ?: ""
    }


    protected var isCollect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initPlayer()

        viewBinding.player.setUp(
            path, "", JzvdStd.SCREEN_NORMAL,
            JZMediaSystemAssertFolder::class.java
        )

        Jzvd.SAVE_PROGRESS = false

        viewBinding.player.startVideo()


        lifecycleScope.launchWhenCreated {
            mCollectViewModel.isCollect(id)
        }

        viewBinding.collectLayout.searchIv.setOnClickListener {
            goActivity(SearchActivity::class.java)
        }

        viewBinding.collectLayout.collectIv.setOnClickListener {
            if (isCollect) {
                mCollectViewModel.delete(belong, id)
            } else {
                mCollectViewModel.insert(belong, id)
            }
            mCollectViewModel.collectStatus.value = !isCollect
        }

        mCollectViewModel.collectStatus.observe(this) {
            isCollect = it
            viewBinding.collectLayout.collectIv.isSelected = it
        }

        PlatformUtil.getInstance(this).addScreenSaverStateListener(screenSaveStateListener)
    }

    private fun registerAudioFocus(){
        am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val attributes = AudioAttributes.Builder()
        attributes.setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
        attributes.setUsage(AudioAttributes.USAGE_MEDIA)
        val builder = AudioFocusRequest.Builder(1)
        builder.setAudioAttributes(attributes.build())
        builder.setAcceptsDelayedFocusGain(false)
        builder.setWillPauseWhenDucked(false)
        builder.setOnAudioFocusChangeListener{

        }
        audioFocus = builder.build()

        am.requestAudioFocus(audioFocus)
    }

    private fun unregisterAudioFocus(){
        am.abandonAudioFocusRequest(audioFocus)
    }


    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.goOnPlayOnPause()
    }

    override fun onResume() {
        super.onResume()
        Jzvd.goOnPlayOnResume();
    }

    override fun onDestroy() {
        super.onDestroy()
        PlatformUtil.getInstance(this).removeScreenSaverStateListener(this.screenSaveStateListener)
        Jzvd.releaseAllVideos()
    }

    abstract fun initPlayer()
    abstract fun isRegisterAudio():Boolean



}