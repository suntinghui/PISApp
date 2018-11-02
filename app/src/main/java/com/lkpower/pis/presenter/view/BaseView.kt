package com.lkpower.pis.presenter.view

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(text:String)
    fun onDataIsNull(){}//默认实现
}