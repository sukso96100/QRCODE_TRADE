package xyz.youngbin.hackpay.ui.activities.seller

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.list_item_seller_product.view.*
import org.json.JSONArray
import network.HPAPI
import xyz.youngbin.hackpay.R
import kotlin.concurrent.thread

class SellerProductListActivity : ListActivity() {
    var products = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar.setDisplayHomeAsUpEnabled(true)

        loadData()
    }

    private fun loadData() {
        thread {
            //val body = JSONObject(mapOf("name" to id, "password" to password))
            val r = HPAPI.get("/product", mapOf("seller_id" to "${HPAPI.seller_id!!}"))

            runOnUiThread {
                if (r == null) {
                    Toast.makeText(this@SellerProductListActivity, getString(R.string.error_network), Toast.LENGTH_LONG).show()
                }else if (r.statusCode != 200) {
                    Toast.makeText(this@SellerProductListActivity, getString(R.string.error_api_etc), Toast.LENGTH_LONG).show()
                }else {
                    products = r.jsonArray
                    listView.adapter = ProductListAdapter()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_seller_product_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_add -> {
                startActivity(Intent(this, SellerProductAddActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class ProductListAdapter: BaseAdapter() {
        private val mInflater = this@SellerProductListActivity.layoutInflater
        override fun getCount(): Int = products.length()
        override fun getItem(position: Int): Any? = null
        override fun getItemId(position: Int): Long = position.toLong()
        override fun getView(position: Int, cView: View?, parent: ViewGroup): View {
            val convertView = cView ?: mInflater.inflate(R.layout.list_item_seller_product, null)

            val product = products.getJSONObject(position)
            convertView.titleLabel.text = product.getString("name")
            convertView.subtitleLabel.text = "여기엔 뭐적지..."
            convertView.priceLabel.text = "￦ ${product.getString("price")}"

            return convertView
        }
    }
}