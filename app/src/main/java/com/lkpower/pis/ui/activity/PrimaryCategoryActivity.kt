package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.AppManager
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.onClick
import com.lkpower.base.utils.PISUtil
import com.lkpower.base.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.Category
import com.lkpower.pis.data.protocol.XJ_LCFC
import com.lkpower.pis.injection.component.DaggerUserComponent
import com.lkpower.pis.injection.module.UserModule
import com.lkpower.pis.presenter.LCFCInstancePresenter
import com.lkpower.pis.presenter.view.LCFCInstanceView
import com.lkpower.pis.ui.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.activity_primary_category.*

class PrimaryCategoryActivity : BaseMvpActivity<LCFCInstancePresenter>(), LCFCInstanceView {

    private lateinit var mCategoryAdapter: CategoryAdapter
    private var pressTime: Long = 0

    private lateinit var shift: List<XJ_LCFC>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_primary_category)

        initView()

        queryLCFC()

    }

    private fun initView() {
        mTitleTv.text = "乘务巡检系统"

        shift = listOf()

        mTitleTv.onClick {
            if (shift.isEmpty())
                queryLCFC()
            else
                selectLCFC()
        }

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

    private fun queryLCFC() {
        mPresenter.getLCFCInstance(AppPrefsUtils.getString(BaseConstant.kEmpId), PISUtil.getTokenKey())
    }

    private fun loadPrimaryData(): MutableList<Category> {
        return mutableListOf<Category>(
                Category(1, "出乘管理", R.drawable.icon_ccgl, 0, "/pis/SecondCategoryActivity"),
                Category(2, "计划任务传达", R.drawable.icon_jhrwcd, 0, "/pis/TaskConveyListActivity"),
                Category(3, "项目确认", R.drawable.icon_xmqr, 0, "/pis/SetoutGroupTaskListActivity"),
                Category(4, "作业任务", R.drawable.icon_zyrw, 0, "/pis/InspectionStationListActivity"),
                Category(5, "行车信息", R.drawable.icon_clxx, 0, "/pis/SecondCategoryActivity"),
                Category(6, "应急反馈", R.drawable.icon_yjfk, 0, "/pis/SecondCategoryActivity"),
                Category(7, "181故障", R.drawable.icon_181gz, 0, "/pis/SecondCategoryActivity"),
                Category(8, "段发信息", R.drawable.icon_dfxx, 0, "/pis/PublishListActivity"),
                Category(9, "退乘管理", R.drawable.icon_tcgl, 0, "/pis/SecondCategoryActivity"),
                Category(10, "学习文件", R.drawable.icon_xxwj, 0, "/pis/LearnDocListActivity"),
                Category(11, "系统设置", R.drawable.icon_setting, 0, "/pis/SettingActivity")
        )
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetLCFCInstanceResult(result: List<XJ_LCFC>) {
        shift = result
        if (shift.isEmpty()) {
            ViewUtils.showSimpleAlert(this, "该账号下没有行车数据")
        } else {
            var item = result.get(0)
            mTitleTv.text = item.ClassName + "#" + item.TrainmanName + "#" + item.SendTime
            PISUtil.setInstanceId(item.ID)
        }
    }

    private fun selectLCFC() {
        if (shift.isEmpty())
            return

        var list: List<String> = shift.map { it.ClassName + "#" + it.TrainmanName + "#" + it.SendTime }
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            PISUtil.setInstanceId(shift.get(options1).ID)
            mTitleTv.text = list.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(list)
        pickerView.setSelectOptions(shift.indexOfFirst { it.ID == PISUtil.getInstanceId() })

        pickerView.show()
    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            ViewUtils.warning(this, "再按一次退出程序")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }

}