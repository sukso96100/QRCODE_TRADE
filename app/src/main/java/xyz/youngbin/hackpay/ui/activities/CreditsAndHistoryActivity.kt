package xyz.youngbin.hackpay.ui.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_credits_and_history.*
import kotlinx.android.synthetic.main.content_credits_and_history.*
import kotlinx.android.synthetic.main.list_header_credits.view.*
import xyz.youngbin.hackpay.R

class CreditsAndHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits_and_history)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val history = ArrayList<HistoryItem>()
        history.add(HistoryItem("구내식당", "2017-11-23", -4200.0))
        history.add(HistoryItem("구내식당", "2017-11-23", -4200.0))
        history.add(HistoryItem("구내식당", "2017-11-23", -4200.0))
        history.add(HistoryItem("편의점", "2017-11-23", -3520.0))
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
    }

    data class HistoryItem(var title: String, var datetime: String, var amount: Double)
    class HistoryItemAdapter(data: ArrayList<HistoryItem>, context: Context) : BaseAdapter(){
        private val data: ArrayList<HistoryItem>
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
}
