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
import com.lkpower.base.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.ui.adapter.CategoryAdapter
import com.lkpower.pis.data.protocol.Category
import com.lkpower.pis.data.protocol.XJ_LCFC
import com.lkpower.pis.injection.component.DaggerUserComponent
import com.lkpower.pis.injection.module.UserModule
import com.lkpower.pis.presenter.LCFCInstancePresenter
import com.lkpower.pis.presenter.view.LCFCInstanceView
import kotlinx.android.synthetic.main.activity_fault_history_list.*
import kotlinx.android.synthetic.main.activity_primary_category.*
import org.jetbrains.anko.toast

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
        mPresenter.getLCFCInstance(AppPrefsUtils.getString(BaseConstant.kEmpId), AppPrefsUtils.getString(BaseConstant.kTokenKey))
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

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetLCFCInstanceResult(result: List<XJ_LCFC>) {
        shift = result
        if (shift.isEmpty()) {
            ViewUtils.showSimpleAlert(this, "该账号下没有行车数据")
        }

    }

    private fun selectLCFC() {
        if (shift.isEmpty())
            return

        var list: List<String> = shift.map { it.ClassName + "-" + it.TrainmanName + "-" + it.SendTime }
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            AppPrefsUtils.putString(BaseConstant.kInstanceId, shift.get(options1).ID)
            mTypeTv.text = list.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(list)
        pickerView.setSelectOptions(shift.indexOf(shift.first { it.ID == AppPrefsUtils.getString(BaseConstant.kInstanceId) }))
        pickerView.show()
    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            toast("再按一次退出程序")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }

}