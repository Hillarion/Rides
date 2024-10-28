package com.thierrystpierre.rides.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.thierrystpierre.rides.databinding.DialogMonitorNoticeBinding

object DialogUtils {
    fun createNoticeDialog(context : Context, title : String?, text : String,
                           btn_text : String? = null,
                           callback : View.OnClickListener? = null) : AlertDialog {

        val viewBinding = DialogMonitorNoticeBinding.inflate(LayoutInflater.from(context), null, false)
        val dialog = AlertDialog.Builder(context).apply {
            setView(viewBinding.root)
            setCancelable(false)
        }.create()
        if (title != null) {
            viewBinding.dialogTitle.text = title
        } else {
            viewBinding.dialogTitle.visibility = View.GONE
        }
        viewBinding.dialogMessage.text = text
        btn_text?.let{ viewBinding.btnOk.text = it }
        viewBinding.btnOk.setOnClickListener {
            dialog.dismiss()
            callback?.onClick(viewBinding.btnOk)
        }
        return dialog
    }
}