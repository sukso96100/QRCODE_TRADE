package xyz.youngbin.hackpay.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_payment.*
import org.json.JSONArray
import xyz.youngbin.hackpay.R
import xyz.youngbin.hackpay.ui.activities.PaymentActivity
import xyz.youngbin.hackpay.ui.adapter.AmountItem

/**
 * Created by youngbin on 2017. 11. 22..
 */
class ConfirmationDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(seller: String, method: String, total: Double, details: ArrayList<AmountItem>): ConfirmationDialog {
            val dialog = ConfirmationDialog()
            val args = Bundle()
            args.putString("seller",seller)
            args.putString("method",method)
            args.putDouble("total",total)
            args.putParcelableArrayList("details", details)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(activity)
        dialog.setContentView(R.layout.dialog_payment)
        dialog.seller.text = arguments.getString("seller")
        dialog.method.text = arguments.getString("method")
        dialog.total.text = arguments.getDouble("total").toString()
        dialog.confirm.setOnClickListener{
            val paymentIntent = Intent(activity, PaymentActivity::class.java)
            paymentIntent.putExtra("seller",arguments.getString("seller"))
            paymentIntent.putExtra("method",arguments.getString("method"))
            paymentIntent.putExtra("total",arguments.getDouble("total"))
            startActivity(paymentIntent)
            activity.finish()
        }
        dialog.total.setOnClickListener{
            val details = AmountDetailsDialog.newInstance(arguments.getParcelableArrayList("details"))
            details.show(fragmentManager, "details")
        }
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        val parentActivity = activity
        if(parentActivity is DialogInterface.OnDismissListener){
            parentActivity.onDismiss(dialog)
        }
    }

}