package xyz.youngbin.hackpay.ui.activities

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*
import xyz.youngbin.hackpay.R

class ResultActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        seller.text = intent.getStringExtra("seller")
        total.text = intent.getDoubleExtra("total", 0.0).toString()
        method.text = intent.getStringExtra("method")
        confirm.setOnClickListener{
            finish()
        }
    }
}
