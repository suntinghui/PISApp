package com.lkpower.pis.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.kotlin.base.ui.activity.BaseActivity
import com.lkpower.base.ext.onClick
import com.lkpower.pis.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        mLoginBtn.onClick(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mLoginBtn -> {
                startActivity<PrimaryCategoryActivity>()
            }
        }

    }
}
