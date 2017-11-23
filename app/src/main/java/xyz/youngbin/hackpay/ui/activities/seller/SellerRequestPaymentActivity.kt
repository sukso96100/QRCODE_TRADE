package xyz.youngbin.hackpay.ui.activities.seller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_seller_request_payment.*
import kotlinx.android.synthetic.main.dialog_payment.*
import kotlinx.android.synthetic.main.list_item_seller_request_product.view.*
import org.json.JSONArray
import org.json.JSONObject
import xyz.youngbin.hackpay.network.HPAPI
import xyz.youngbin.hackpay.R
import kotlin.concurrent.thread

class SellerRequestPaymentActivity : Activity() {
    var products = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_request_payment)
        actionBar.setDisplayHomeAsUpEnabled(true)

        loadData()
    }

    private fun loadData() {
        thread {
            val r = HPAPI.get("/product", mapOf("seller_id" to "${HPAPI.seller_id!!}"))

            runOnUiThread {
                if (r == null) {
                    Toast.makeText(this@SellerRequestPaymentActivity, getString(R.string.error_network), Toast.LENGTH_LONG).show()
                }else if (r.statusCode != 200) {
                    Toast.makeText(this@SellerRequestPaymentActivity, getString(R.string.error_api_etc), Toast.LENGTH_LONG).show()
                }else {
                    products = r.jsonArray
                    for (i in 0 until products.length()) {
                        products.getJSONObject(i).put("count", 0)
                    }
                    listView.adapter = ProductListAdapter()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_seller_request_payment, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_done -> {
                thread {
                    val purchaseProducts = mutableListOf<Map<String, String>>()
                    for (i in 0 until products.length()) {
                        val product = products.getJSONObject(i)
                        if (product.getInt("count") > 0) {
                            purchaseProducts.add(mapOf("id" to product.getString("id"), "count" to product.getString("count")))
                        }
                    }
                    val json = JSONObject()
                    json.put("purchase_products", JSONArray(purchaseProducts))
                    val r = HPAPI.post("/trade", mapOf(), json)

                    if (r == null) {
                        runOnUiThread {
                            Toast.makeText(this@SellerRequestPaymentActivity, getString(R.string.error_network), Toast.LENGTH_LONG).show()
                        }
                    }else if (r.statusCode != 201) {
                        runOnUiThread {
                            Toast.makeText(this@SellerRequestPaymentActivity, getString(R.string.error_api_etc), Toast.LENGTH_LONG).show()
                        }
                    }else {
                        val rj = r.jsonObject

                        try {
                            val qr_r = khttp.get("http://qr.youngbin.xyz/qr", params=mapOf("idx" to rj.getString("new_trade_id"), "uuid" to rj.getString("uuid")))
                            if (qr_r.statusCode != 200) {
                                runOnUiThread {
                                    Toast.makeText(this@SellerRequestPaymentActivity, getString(R.string.error_api_etc), Toast.LENGTH_LONG).show()
                                }
                            }else {
                                val qr_rj = qr_r.jsonObject
                                runOnUiThread {
                                    val pushIntent = Intent(this@SellerRequestPaymentActivity, SellerPaymentWaitActivity::class.java)
                                    pushIntent.putExtra("qr_url", qr_rj.getString("url"))
                                    startActivity(pushIntent)
                                }
                            }
                        }catch (e: Exception) {
                            runOnUiThread {
                                Toast.makeText(this@SellerRequestPaymentActivity, getString(R.string.error_network), Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class ProductListAdapter: BaseAdapter() {
        private val mInflater = this@SellerRequestPaymentActivity.layoutInflater
        override fun getCount(): Int = products.length()
        override fun getItem(position: Int): Any? = null
        override fun getItemId(position: Int): Long = position.toLong()
        override fun getView(position: Int, cView: View?, parent: ViewGroup): View {
            val convertView = cView ?: mInflater.inflate(R.layout.list_item_seller_request_product, null)

            val product = products.getJSONObject(position)
            convertView.titleLabel.text = product.getString("name")
            convertView.priceLabel.text ="￦ ${product.getString("price")}"
            convertView.btnMinus.setOnClickListener {
                val currentValue = product.getInt("count")
                if (currentValue > 0) {
                    convertView.countLabel.text = "${currentValue - 1}"
                    product.put("count", currentValue-1)
                    totalPriceLabel.text = "${totalPriceLabel.text.toString().toInt() - product.getInt("price")}"
                }
            }
            convertView.btnPlus.setOnClickListener {
                val currentValue = product.getInt("count")
                convertView.countLabel.text = "${currentValue + 1}"
                product.put("count", currentValue+1)
                totalPriceLabel.text = "${totalPriceLabel.text.toString().toInt() + product.getInt("price")}"
            }

            return convertView
        }
    }
}