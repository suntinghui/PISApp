package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.bigkoo.alertview.AlertView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.widgets.ImagePickerView
import com.lkpower.base.common.BaseApplication.Companion.context
import com.lkpower.base.ext.onClick
import com.lkpower.base.ext.setVisible
import com.lkpower.base.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.RiskItem
import com.lkpower.pis.data.protocol.SetoutConfirmProj
import kotlinx.android.synthetic.main.layout_confirm_proj.view.*

class ConfirmProjAdapter(context: Context) : BaseRecyclerViewAdapter<SetoutConfirmProj, ConfirmProjAdapter.ViewHolder>(context) {

    private lateinit var listener: SubmitConfirmProjListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_confirm_proj, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val model = dataList[position]

        holder.itemView.mProjectNameTv.text = model.ProjectName
        holder.itemView.mConfirmBtn.onClick {
            if (holder.itemView.mContentEt.text.isNullOrEmpty())
                Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show()

            listener?.onSubmit(position, holder.itemView.mContentEt.text.toString())
        }
    }

    fun setOnSubmitConfirmProjListener(listener: SubmitConfirmProjListener) {
        this.listener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface SubmitConfirmProjListener {
        fun onSubmit(position: Int, content: String)
    }

}