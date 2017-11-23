package xyz.youngbin.hackpay.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_charge_or_refund.*
import xyz.youngbin.hackpay.network.HPAPI
import org.json.JSONObject
import xyz.youngbin.hackpay.R
import kotlin.concurrent.thread

class ChargeOrRefundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge_or_refund)

        confirm.setOnClickListener {
            thread {
                val body = JSONObject(mapOf("amount" to amount.text.toString()))
                val res = HPAPI.post("/balance", mapOf(), body)
                runOnUiThread {
                    if (res == null) {
                        Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_LONG).show()
                    }else if (res.statusCode != 200) {
                        Toast.makeText(this, getString(R.string.error_api_etc), Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(this, getString(R.string.credits_charge_complete), Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }
}
