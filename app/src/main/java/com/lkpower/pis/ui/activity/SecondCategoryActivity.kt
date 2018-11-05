package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.lkpower.pis.ui.activity.BaseActivity
import com.lkpower.pis.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.Category
import com.lkpower.pis.ui.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.activity_second_category.*
import org.jetbrains.anko.toast
import java.lang.Exception

@Route(path = "/pis/SecondCategoryActivity")
class SecondCategoryActivity : BaseActivity() {

    @Autowired(name = "id")
    @JvmField
    var parentId: Int = 0

    @Autowired
    @JvmField
    var title: String = "乘务巡检"

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

        try {
            mCategoryAdapter = CategoryAdapter(this)
            mCategoryRv.adapter = mCategoryAdapter
            mCategoryAdapter.setData(loadSecondCategoryData().get(parentId)!!)
            mCategoryAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
                override fun onItemClick(item: Category, position: Int) {
                    // 此处不允许传递参数，推送处也有使用，否则会出现问题
                    ARouter.getInstance().build(item.action)
                            .navigation()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            ViewUtils.error(this, "菜单结构设置异常")
        }

    }

    private fun loadSecondCategoryData(): HashMap<Int, ArrayList<Category>> {
        return hashMapOf(
                1 to arrayListOf<Category>(
                        Category(100, "报到", R.drawable.icon_bd, 1, "/pis/SetoutCheckinListActivity"),
                        Category(101, "酒测", R.drawable.icon_jc, 1, "/pis/SetoutAlcoholTestListActivity"),
                        Category(102, "出乘确认", R.drawable.icon_ccqr, 1, "/pis/SetoutListActivity")),
                2 to arrayListOf<Category>(),
                3 to arrayListOf<Category>(),
                4 to arrayListOf<Category>(),
                5 to arrayListOf<Category>(
                        Category(501, "上报信息", R.drawable.icon_sbxx, 5, "/pis/DrivingInfoUploadActivity"),
                        Category(502, "历史信息", R.drawable.icon_lsxx, 5, "/pis/DrivingInfoListActivity")
                ),
                6 to arrayListOf<Category>(
                        Category(601, "应急反馈", R.drawable.icon_yjfk, 6, "/pis/EmergencyInfoAddActivity"),
                        Category(602, "历史应急", R.drawable.icon_lsxx, 6, "/pis/EmergencyInfoListActivity")
                ),
                7 to arrayListOf<Category>(
                        Category(701, "故障反馈", R.drawable.icon_yjfk, 7, "/pis/FaultReportActivity"),
                        Category(702, "历史故障", R.drawable.icon_lsxx, 7, "/pis/FaultHistoryListActivity")),
                8 to arrayListOf<Category>(),
                9 to arrayListOf<Category>(
                        Category(901, "报到", R.drawable.icon_bd, 9, "/pis/SetoffCheckinListActivity"),
                        Category(902, "酒测", R.drawable.icon_jc, 9, "/pis/SetoffAlcoholTestListActivity"),
                        Category(903, "退乘确认", R.drawable.icon_tcgl, 9, "/pis/SetoffListActivity")),
                10 to arrayListOf<Category>(),
                11 to arrayListOf<Category>()
        )

    }
}