package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.common.AppManager
import com.lkpower.pis.R
import com.lkpower.pis.ui.adapter.CategoryAdapter
import com.lkpower.pis.data.protocol.Category
import kotlinx.android.synthetic.main.activity_primary_category.*
import org.jetbrains.anko.toast

class PrimaryCategoryActivity : BaseActivity() {

    private lateinit var mCategoryAdapter: CategoryAdapter
    private var pressTime:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_primary_category)

        initView()
    }

    private fun initView() {
        mCategoryRv.layoutManager = GridLayoutManager(this, 3)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mCategoryRv);

        mCategoryAdapter = CategoryAdapter(this)
        mCategoryRv.adapter = mCategoryAdapter
        mCategoryAdapter.setData(loadPrimaryData())
        mCategoryAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                ARouter.getInstance().build(item.action)
                        .withInt("id", item.id)
                        .withString("title", item.title)
                        .navigation();
            }
        })

    }

    private fun loadPrimaryData(): MutableList<Category> {
        return mutableListOf<Category>(
                Category(1, "出乘管理", R.drawable.icon_ccgl, 0, "/pis/SecondCategoryActivity"),
                Category(2, "作业任务", R.drawable.icon_zyrw, 0, "/pis/ZYRWListActivity"),
                Category(3, "行车信息", R.drawable.icon_clxx, 0, "/pis/SecondCategoryActivity"),
                Category(4, "应急反馈", R.drawable.icon_yjfk, 0, "/pis/SecondCategoryActivity"),
                Category(5, "181故障", R.drawable.icon_181gz, 0, "/pis/SecondCategoryActivity"),
                Category(6, "段发信息", R.drawable.icon_dfxx, 0, "/pis/PublishListActivity"),
                Category(7, "退乘管理", R.drawable.icon_tcgl, 0, "/pis/SecondCategoryActivity"),
                Category(8, "学习文件", R.drawable.icon_xxwj, 0, "/pis/SecondCategoryActivity"),
                Category(9, "系统设置", R.drawable.icon_setting, 0, "/pis/SettingActivity")
        )
    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000){
            toast("再按一次退出程序")
            pressTime = time
        } else{
            AppManager.instance.exitApp(this)
        }
    }
}