package xyz.youngbin.hackpay.ui.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import xyz.youngbin.hackpay.R
import xyz.youngbin.hackpay.ui.dialogs.AmountDetailsDialog

/**
 * Created by youngbin on 2017. 11. 23..
 */

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