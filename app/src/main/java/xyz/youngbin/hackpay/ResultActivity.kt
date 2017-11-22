package xyz.youngbin.hackpay

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

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
