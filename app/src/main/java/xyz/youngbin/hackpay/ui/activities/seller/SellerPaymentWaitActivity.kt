package xyz.youngbin.hackpay.ui.activities.seller

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_seller_payment_wait.*
import xyz.youngbin.hackpay.R

class SellerPaymentWaitActivity: Activity() {
    private var products = listOf<Map<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_payment_wait)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val qr_url = intent.getStringExtra("qr_url")
        Picasso.with(this).load(qr_url).into(imageView)

        totalPriceLabel.text = intent.getIntExtra("totalPrice", 0).toString()
        products = intent.getSerializableExtra("products") as List<Map<String, String>>
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}