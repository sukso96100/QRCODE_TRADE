package xyz.youngbin.hackpay.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_amount.*
import kotlinx.android.synthetic.main.list_header_history_details.view.*
import xyz.youngbin.hackpay.R
import xyz.youngbin.hackpay.ui.adapter.AmountItem
import xyz.youngbin.hackpay.ui.adapter.AmountItemAdapter

/**
 * Created by youngbin on 2017. 11. 23..
 */
class HistoryDetailsDialog: BottomSheetDialogFragment() {
    companion object {
        fun newInstance(seller: String, method: String, total: Double,
                        datetime: String, details: ArrayList<AmountItem>): ConfirmationDialog {
            val dialog = ConfirmationDialog()
            val args = Bundle()
            args.putString("seller",seller)
            args.putString("method",method)
            args.putString("datetime", datetime)
            args.putDouble("total",total)
            args.putParcelableArrayList("details", details)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(activity)
        dialog.setContentView(R.layout.layout_listview_only)

        val details = arguments.getParcelableArrayList<AmountItem>("details")
        dialog.listView.adapter = AmountItemAdapter(details, activity)

        val header = layoutInflater.inflate(R.layout.list_header_history_details, null)
        header.total.text = arguments.getDouble("total").toString()
        header.seller.text = arguments.getString("seller")
        header.datetime.text = arguments.getString("datetime")
        header.method.text = arguments.getString("method")

        dialog.listView.addHeaderView(header)

        return dialog
    }
}