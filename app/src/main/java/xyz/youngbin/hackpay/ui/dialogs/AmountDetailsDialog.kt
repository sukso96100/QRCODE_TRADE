package xyz.youngbin.hackpay.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_amount.*
import xyz.youngbin.hackpay.R
import xyz.youngbin.hackpay.ui.adapter.AmountItem
import xyz.youngbin.hackpay.ui.adapter.AmountItemAdapter

/**
 * Created by youngbin on 2017. 11. 22..
 */
class AmountDetailsDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(data: ArrayList<AmountItem>): AmountDetailsDialog {
            val dialog = AmountDetailsDialog()
            val args = Bundle()
            args.putParcelableArrayList("data", data)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(activity)
        val data: ArrayList<AmountItem> = arguments.getParcelableArrayList<AmountItem>("data")
        dialog.setContentView(R.layout.dialog_amount)
        dialog.listView.adapter = AmountItemAdapter(data, activity)
        return dialog
    }





}