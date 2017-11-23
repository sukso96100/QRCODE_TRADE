package xyz.youngbin.hackpay.ui.activities.seller

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_seller_request_payment.*
import kotlinx.android.synthetic.main.list_item_seller_request_product.view.*
import xyz.youngbin.hackpay.R

class SellerRequestPaymentActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_request_payment)
        actionBar.setDisplayHomeAsUpEnabled(true)

        listView.adapter = ProductListAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_seller_request_payment, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    inner class ProductListAdapter: BaseAdapter() {
        private val mInflater = this@SellerRequestPaymentActivity.layoutInflater
        override fun getCount(): Int = 15
        override fun getItem(position: Int): Any? = null
        override fun getItemId(position: Int): Long = position.toLong()
        override fun getView(position: Int, cView: View?, parent: ViewGroup): View {
            val convertView = cView ?: mInflater.inflate(R.layout.list_item_seller_request_product, null)

            convertView.titleLabel.text = "뺴뺴로"
            convertView.priceLabel.text = "1,000"
            convertView.btnMinus.setOnClickListener {
                convertView.countLabel.text = "${convertView.countLabel.text.toString().toInt() - 1}"
            }
            convertView.btnPlus.setOnClickListener {
                convertView.countLabel.text = "${convertView.countLabel.text.toString().toInt() + 1}"
            }

            return convertView
        }
    }
}