package xyz.youngbin.hackpay.ui.activities.seller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_seller_product_add.*
import org.json.JSONObject
import xyz.youngbin.hackpay.network.HPAPI
import xyz.youngbin.hackpay.R
import kotlin.concurrent.thread

class SellerProductAddActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_product_add)
        actionBar.setDisplayHomeAsUpEnabled(true)

        btn_add.setOnClickListener {
            val prodName = textProdName.text.toString()
            val prodPrice = textProdPrice.text.toString()
            if (prodName == "" || prodPrice == "") {
                Toast.makeText(this, "내용을 채워주세요.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            thread {
                val json = JSONObject(mapOf("name" to prodName, "price" to prodPrice))
                val r = HPAPI.post("/product", mapOf(), json)

                runOnUiThread {
                    if (r == null) {
                        Toast.makeText(this@SellerProductAddActivity, getString(R.string.error_network), Toast.LENGTH_LONG).show()
                    }else if (r.statusCode != 201) {
                        Toast.makeText(this@SellerProductAddActivity, getString(R.string.error_api_etc), Toast.LENGTH_LONG).show()
                    }else {
                        onBackPressed()
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}