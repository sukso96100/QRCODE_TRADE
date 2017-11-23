package xyz.youngbin.hackpay.ui.activities.seller

import android.app.ListActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.list_item_seller_product.view.*
import xyz.youngbin.hackpay.R

class SellerProductList: ListActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listView.adapter = ProductListAdapter()
    }

    inner class ProductListAdapter: BaseAdapter() {
        private val mInflater = this@SellerProductList.layoutInflater
        override fun getCount(): Int = 5
        override fun getItem(position: Int): Any? = null
        override fun getItemId(position: Int): Long = position.toLong()
        override fun getView(position: Int, cView: View?, parent: ViewGroup): View {
            val convertView = cView ?: mInflater.inflate(R.layout.list_item_seller_product, null)

            convertView.titleLabel.text = "뺴뺴로"
            convertView.subtitleLabel.text = "여기엔 뭐적지..."
            convertView.priceLabel.text = "￦ 10,000"

            return convertView
        }
    }
}