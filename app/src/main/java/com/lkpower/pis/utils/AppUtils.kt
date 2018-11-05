package com.lkpower.pis.utils

import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import com.orhanobut.logger.Logger


object AppUtils {

    fun checkVoice(context:Context) {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val max = am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)
        val current = am.getStreamVolume(AudioManager.STREAM_NOTIFICATION)
        Logger.e("VOICE: $max - $current")

        if (current < 2) {
            ViewUtils.warning(context, "请调高手机音量，否则可能无法听到系统通知")
        }
    }
}