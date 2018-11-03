package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.lkpower.pis.widgets.ProgressLoading
import com.lkpower.pis.common.BaseApplication
import com.lkpower.pis.injection.component.ActivityComponent
import com.lkpower.pis.injection.component.DaggerActivityComponent
import com.lkpower.pis.injection.module.ActivityModule
import com.lkpower.pis.injection.module.LifecycleProviderModule
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.utils.ViewUtils
import org.jetbrains.anko.toast
import java.lang.Exception
import javax.inject.Inject

/*
    Activity基类，业务相关
 */
abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {

    //Presenter泛型，Dagger注入
    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()

        //初始加载框
        mLoadingDialog = ProgressLoading.create(this)

        //ARouter注册
        ARouter.getInstance().inject(this)
    }

    /*
        Dagger注册
     */
    protected abstract fun injectComponent()

    /*
        初始Activity Component
     */
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder().appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

    }

    /*
        显示加载框，默认实现
     */
    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    /*
        隐藏加载框，默认实现
     */
    override fun hideLoading() {
        try {
            mLoadingDialog.hideLoading()
        } catch (e: Exception) {

        }
    }

    /*
        错误信息提示，默认实现
     */
    override fun onError(text: String) {
        this.hideLoading()

        ViewUtils.error(this, text)
    }
}
