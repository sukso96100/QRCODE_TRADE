package xyz.youngbin.hackpay

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_amount.*

/**
 * Created by youngbin on 2017. 11. 22..
 */
class AmountDetailsDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(data: ArrayList<AmountItem>): AmountDetailsDialog{
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

    data class AmountItem(val title: String, val amount: Double) : Parcelable {
        constructor(source: Parcel) : this(
                source.readString(),
                source.readDouble()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(title)
            writeDouble(amount)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<AmountItem> = object : Parcelable.Creator<AmountItem> {
                override fun createFromParcel(source: Parcel): AmountItem = AmountItem(source)
                override fun newArray(size: Int): Array<AmountItem?> = arrayOfNulls(size)
            }
        }
    }


    class AmountItemAdapter(data: ArrayList<AmountItem>, context: Context) : BaseAdapter(){
        private val data: ArrayList<AmountItem>
        private val context: Context

        init {
            this.data = data
            this.context = context
        }


        override fun getCount(): Int {
            return this.data.size
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getItem(position: Int): Any {
            return this.data[position]
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            var holder: ViewHolder
            var inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if(convertView==null){
                view = inflator.inflate(R.layout.list_item_amount, parent, false)
                holder = ViewHolder(
                        view.findViewById(R.id.title),
                        view.findViewById(R.id.amount))
                view.tag = holder
            }else{
                view = convertView
                holder = view.tag as ViewHolder
            }

            holder.txtTitle.text = data[position].title
            holder.txtAmount.text = data[position].amount.toString()

            return view
        }

        data class ViewHolder (val txtTitle: TextView, val txtAmount: TextView)
    }

}