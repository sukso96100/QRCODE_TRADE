package xyz.youngbin.hackpay

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_payment.*

/**
 * Created by youngbin on 2017. 11. 22..
 */
class ConfirmationDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(seller: String, method: String, total: Double): ConfirmationDialog{
            val dialog = ConfirmationDialog()
            val args = Bundle()
            args.putString("seller",seller)
            args.putString("method",method)
            args.putDouble("total",total)
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
            val paymentIntent = Intent(activity, ResultActivity::class.java)
            paymentIntent.putExtra("seller",arguments.getString("seller"))
            paymentIntent.putExtra("method",arguments.getString("method"))
            paymentIntent.putExtra("total",arguments.getDouble("total"))
            startActivity(paymentIntent)
            activity.finish()
        }
        dialog.total.setOnClickListener{
            var data = ArrayList<AmountDetailsDialog.AmountItem>()
            data.add(AmountDetailsDialog.AmountItem("Hello", 50.0))
            data.add(AmountDetailsDialog.AmountItem("World", 50.0))
            val details = AmountDetailsDialog.newInstance(data)
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