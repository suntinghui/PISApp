package com.lkpower.pis.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lkpower.pis.widgets.ProgressLoading
import com.lkpower.pis.common.BaseApplication
import com.lkpower.pis.injection.component.ActivityComponent
import com.lkpower.pis.injection.component.DaggerActivityComponent
import com.lkpower.pis.injection.module.ActivityModule
import com.lkpower.pis.injection.module.LifecycleProviderModule
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.utils.ViewUtils
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast
import java.lang.Exception
import javax.inject.Inject

/*
    Fragment基类，业务相关
 */
abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog:ProgressLoading


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initActivityInjection()
        injectComponent()

        //初始加载框
        mLoadingDialog = ProgressLoading.create(context!!)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            hideLoading()
        } catch (e:Exception) {
        }
    }

    /*
        Dagger注册
     */
    protected abstract fun injectComponent()

    /*
        初始化Activity级别Component
     */
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder().appComponent((act.application as BaseApplication).appComponent)
                .activityModule(ActivityModule(act))
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
        mLoadingDialog.hideLoading()
    }

    /*
        错误信息提示，默认实现
     */
    override fun onError(text:String) {
        ViewUtils.showSimpleAlert(act, text)
    }
}
