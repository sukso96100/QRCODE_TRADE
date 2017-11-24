package xyz.youngbin.hackpay.ui.activities

import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import kotlinx.android.synthetic.main.activity_scanner2.*
import xyz.youngbin.hackpay.R
import xyz.youngbin.hackpay.network.HPAPI
import xyz.youngbin.hackpay.ui.adapter.AmountItem
import xyz.youngbin.hackpay.ui.dialogs.ConfirmationDialog
import kotlin.concurrent.thread

/**
 * Created by youngbin on 2017. 11. 24..
 */
class
ScannerActivity2 : AppCompatActivity(), DialogInterface.OnDismissListener {
    override fun onDismiss(dialog: DialogInterface?) {
        scanner.decodeSingle(qrCallback)
    }

    private val qrCallback = object : BarcodeCallback{
        override fun barcodeResult(result: BarcodeResult?) {
            if(isValidQrContent(result!!.text)){
                val code = result.text

                val codearr = code.replace("hackpay://","").split("-")
                val url = "/trade/${codearr[0]}?uuid=${codearr[1]}-${codearr[2]}-${codearr[3]}-${codearr[4]}-${codearr[5]}"
                thread {
                    val res = HPAPI.get(url, mapOf())
                    runOnUiThread {
                        if (res == null) {
                            scanner.setStatusText(getString(R.string.error_network))
                        }else if (res.statusCode != 200) {
                            scanner.setStatusText(getString(R.string.error_api_etc))
                        }else {
                            var details = ArrayList<AmountItem>()
                            val arr = res.jsonObject.getJSONArray("products")
                            for(i in 0..arr.length()-1){
                                details.add(AmountItem(
                                        arr.getJSONObject(i).getString("product_name"),
                                        arr.getJSONObject(i).getInt("count").toDouble()))
                            }
                            val myDialog = ConfirmationDialog.newInstance(
                                    res.jsonObject.getJSONObject("seller_info").getString("name"),
                                    "cash",
                                    res.jsonObject.getJSONObject("trade_info").getInt("total_price").toDouble(),
                                    details
                                    )
                            myDialog.show(supportFragmentManager,"dialog")
                        }
                    }

                }
            }else{
                scanner.setStatusText(getString(R.string.activity_qr_error))
                scanner.decodeSingle(this)
            }
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner2)

        scanner.decodeSingle(qrCallback)
    }

    override fun onResume() {
        super.onResume()
        scanner.resume()
    }

    override fun onPause() {
        super.onPause()
        scanner.pause()
    }

    fun isValidQrContent(qrContent: String): Boolean{
        val regex = "hackpay:\\/\\/[0-9]+-[0-9a-zA-Z]{8}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{12}"
        return qrContent.matches(Regex(regex))
    }
}