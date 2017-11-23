package xyz.youngbin.hackpay.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_amount.*
import kotlinx.android.synthetic.main.dialog_history_details.*
import xyz.youngbin.hackpay.R
import xyz.youngbin.hackpay.ui.adapter.AmountItem
import xyz.youngbin.hackpay.ui.adapter.AmountItemAdapter

/**
 * Created by youngbin on 2017. 11. 23..
 */
class HistoryDetailsDialog: BottomSheetDialogFragment() {
    companion object {
        fun newInstance(seller: String, method: String, total: Int,
                        datetime: String, details: ArrayList<AmountItem>): HistoryDetailsDialog {
            val dialog = HistoryDetailsDialog()
            val args = Bundle()
            args.putString("seller",seller)
            args.putString("method",method)
            args.putString("datetime", datetime)
            args.putInt("total",total)
            args.putParcelableArrayList("details", details)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog = BottomSheetDialog(activity)
        dialog.setContentView(R.layout.dialog_history_details)

        val details = arguments.getParcelableArrayList<AmountItem>("details")
        dialog.details.adapter = AmountItemAdapter(details, activity)
        dialog.total.text = arguments.getDouble("total").toString()
        dialog.seller.text = arguments.getString("seller")
        dialog.datetime.text = arguments.getString("datetime")
        dialog.method.text = arguments.getString("method")

        return dialog
    }
}