package xyz.youngbin.hackpay.ui.activities

import android.content.Context
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_credits_and_history.*
import kotlinx.android.synthetic.main.layout_listview_only.*
import kotlinx.android.synthetic.main.list_header_credits.view.*
import kotlinx.android.synthetic.main.list_item_amount.*
import xyz.youngbin.hackpay.R
import xyz.youngbin.hackpay.ui.adapter.AmountItem
import xyz.youngbin.hackpay.ui.adapter.AmountItemAdapter
import xyz.youngbin.hackpay.ui.dialogs.HistoryDetailsDialog

class CreditsAndHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits_and_history)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val amountDetails = ArrayList<AmountItem>()
        amountDetails.add(AmountItem("Hello", 50.0))
        amountDetails.add(AmountItem("World", 50.0))

        val history = ArrayList<HistoryItem>()

        // Example Data

        history.add(HistoryItem("구내식당", "2017-11-23", -4200.0, "cash"))
        history.add(HistoryItem("구내식당", "2017-11-23", -4200.0, "cash"))
        history.add(HistoryItem("구내식당", "2017-11-23", -4200.0, "cash"))
        history.add(HistoryItem("편의점", "2017-11-23", -3520.0, "cash"))
        val adapter = HistoryItemAdapter(history, this)
        listView.adapter = adapter

        val inflator = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val header: ViewGroup = inflator.inflate(R.layout.list_header_credits,null) as ViewGroup
        header.balance.text = "100000 KRW"
        header.charge.setOnClickListener {
            // 충전 버튼 동작

        }
        header.refund.setOnClickListener {
            // 환급 버튼 동작
        }
        listView.addHeaderView(header, null, false)
        listView.setOnItemClickListener{
            parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val pos = position-1
            val dialog = HistoryDetailsDialog.newInstance(
                    history[pos].title, history[pos].method,
                    history[pos].amount, history[pos].datetime,
                    amountDetails
            )
            dialog.show(supportFragmentManager, "history-details")
        }
    }

    data class HistoryItem(var title: String, var datetime: String, var amount: Double, var method: String)
    class HistoryItemAdapter(data: ArrayList<HistoryItem>, context: Context) : BaseAdapter(){
        private val data: ArrayList<HistoryItem>
        private val context: Context
        init {
            this.data = data
            this.context = context
        }

        override fun getCount(): Int =this.data.size
        override fun getItemId(position: Int): Long = position.toLong()
        override fun getItem(position: Int): Any = this.data[position]

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val holder: ViewHolder
            val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if(convertView==null){
                view = inflator.inflate(R.layout.list_item_history, parent, false)
                holder = ViewHolder(
                        view.findViewById(R.id.title),
                        view.findViewById(R.id.datetime),
                        view.findViewById(R.id.amount))
                view.tag = holder
            }else{
                view = convertView
                holder = view.tag as ViewHolder
            }

            holder.txtTitle.text = data[position].title
            holder.txtDateTime.text = data[position].datetime
            holder.txtAmount.text = data[position].amount.toString()

            return view
        }

        data class ViewHolder (val txtTitle: TextView, val txtDateTime: TextView, val txtAmount: TextView)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true}
        }
        return super.onOptionsItemSelected(item)
    }
}
