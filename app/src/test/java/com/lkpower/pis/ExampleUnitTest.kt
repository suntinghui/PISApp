package com.lkpower.pis

import com.google.gson.Gson
import com.lkpower.pis.data.protocol.PageBean
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var str = Gson().toJson(PageBean(1.toString(),2.toString(),3.toString(),4.toString()))
        println(str)

        var page = Gson().fromJson(str, PageBean::class.java)
        println(page)
    }
}
