package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.Category
import com.lkpower.pis.ui.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.activity_second_category.*

@Route(path="/pis/SecondCategoryActivity")
class SecondCategoryActivity : BaseActivity() {

    @Autowired(name="id")
    @JvmField
    var parentId:Int = 0

    @Autowired
    @JvmField
    var title:String = "乘务巡检"

    private lateinit var mCategoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second_category)

        //ARouter注册
        ARouter.getInstance().inject(this)

        initView()
    }

    private fun initView() {
        mHeaderBar.setTitleText(title)

        mCategoryRv.layoutManager = GridLayoutManager(this, 3)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mCategoryRv);

        mCategoryAdapter = CategoryAdapter(this)
        mCategoryRv.adapter = mCategoryAdapter
        mCategoryAdapter.setData(loadSecondCategoryData().get(parentId)!!)
        mCategoryAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                ARouter.getInstance().build(item.action)
                        .withInt("id", item.id)
                        .withString("title", item.title)
                        .navigation();
            }
        })

    }

    private fun loadSecondCategoryData(): HashMap<Int, ArrayList<Category>> {
        return hashMapOf(
                1 to arrayListOf<Category>(
                        Category(100, "报到", R.drawable.icon_bd, 1, "/pis/OutCheckinListActivity"),
                        Category(101, "酒测", R.drawable.icon_jc, 1, ""),
                        Category(102, "计划任务传达", R.drawable.icon_jhrwcd, 1, ""),
                        Category(103, "项目确认", R.drawable.icon_xmqr, 1, ""),
                        Category(104, "出乘确认", R.drawable.icon_ccqr, 1, "")),
                2 to arrayListOf<Category>(),
                3 to arrayListOf<Category>(
                        Category(301, "上报信息", R.drawable.icon_sbxx, 3, ""),
                        Category(302, "历史信息", R.drawable.icon_lsxx, 3, "")
                ),
                4 to arrayListOf<Category>(
                        Category(401, "应急反馈", R.drawable.icon_yjfk, 4, ""),
                        Category(402, "历史应急", R.drawable.icon_lsxx, 4, "")
                ),
                5 to arrayListOf<Category>(
                        Category(501, "故障反馈", R.drawable.icon_yjfk, 5, "/pis/FaultReportActivity"),
                        Category(502, "历史故障", R.drawable.icon_lsxx, 5, "/pis/FaultHistoryListActivity")),
                6 to arrayListOf<Category>(),
                7 to arrayListOf<Category>(
                        Category(701, "报到", R.drawable.icon_bd, 7, ""),
                        Category(702, "酒测", R.drawable.icon_jc, 7, ""),
                        Category(703, "退乘确认", R.drawable.icon_tcgl, 7, "")),
                8 to arrayListOf<Category>()
                )

    }
}