package com.lkpower.base.utils

import android.content.Context
import com.blankj.utilcode.util.PathUtils
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.BaseDownloadTask
import android.R.attr.path
import android.widget.Toast
import com.kotlin.base.widgets.ProgressLoading
import com.orhanobut.logger.Logger
import java.io.File


object DownloadFileUtils {

    private lateinit var mLoadingDialog: ProgressLoading

    fun downloadAndOpenFile(context: Context, fileName: String, url: String) {

        mLoadingDialog = ProgressLoading.create(context)

        FileDownloader.setup(context)

        FileDownloader.getImpl().create(url)
                .setPath(PathUtils.getExternalAppDownloadPath() + File.separator + fileName, false)
                .setAutoRetryTimes(2)
                .setListener(object : FileDownloadListener() {
                    override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

                    override fun connected(task: BaseDownloadTask?, etag: String?, isContinue: Boolean, soFarBytes: Int, totalBytes: Int) {
                        mLoadingDialog.showLoading()
                    }

                    override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

                    override fun blockComplete(task: BaseDownloadTask?) {}

                    override fun retry(task: BaseDownloadTask?, ex: Throwable?, retryingTimes: Int, soFarBytes: Int) {}

                    override fun completed(task: BaseDownloadTask) {
                        mLoadingDialog.hideLoading()
                        Toast.makeText(context, "文件下载成功，正在打开文件", Toast.LENGTH_SHORT).show()

                        // 打开文件
                        FileUtils.openFile(context, task.path)
                    }

                    override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

                    override fun error(task: BaseDownloadTask, e: Throwable) {
                        mLoadingDialog.hideLoading()
                        ViewUtils.showSimpleAlert(context, "文件下载出错，请重试")
                    }

                    override fun warn(task: BaseDownloadTask) {}

                }).start()


    }


}