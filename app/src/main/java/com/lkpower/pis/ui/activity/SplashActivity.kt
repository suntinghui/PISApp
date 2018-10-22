package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import com.kotlin.base.ui.activity.BaseActivity
import com.lkpower.pis.R
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash)

        TimerCount(3000, 1000).start()
    }

    inner class TimerCount(val millisInFuture: Long, val countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
        }

        override fun onFinish() {
            this@SplashActivity.startActivity<LoginActivity>()
            this@SplashActivity.finish()
        }

    }
}