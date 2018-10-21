package com.lkpower.pis.data.protocol

/*
学习文件
 */
data class LearnDoc(
        val DocId: String,
        val DocTitle: String,
        val DocRemark: String,
        val PublishDate: String,
        val Enable: String,
        val Uploader: String,
        val UploaderName: String,
        val UploadDate: String,
        val AttList: List<AttModel>
)