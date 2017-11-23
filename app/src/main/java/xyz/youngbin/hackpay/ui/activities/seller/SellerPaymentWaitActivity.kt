package xyz.youngbin.hackpay.ui.activities.seller

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import xyz.youngbin.hackpay.R

class SellerPaymentWaitActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_wait)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}