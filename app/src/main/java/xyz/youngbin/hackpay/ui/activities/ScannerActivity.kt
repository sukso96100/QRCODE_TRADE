package xyz.youngbin.hackpay.ui.activities

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.zxing.Result

import kotlinx.android.synthetic.main.activity_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import xyz.youngbin.hackpay.R
import xyz.youngbin.hackpay.network.HPAPI
import xyz.youngbin.hackpay.ui.dialogs.ConfirmationDialog

class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, DialogInterface.OnDismissListener{


    override fun onDismiss(dialog: DialogInterface?) {
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    private lateinit var mScannerView: ZXingScannerView

    override fun handleResult(rawResult: Result?) {
        if(isValidQrContent(rawResult!!.text)){
//            HPAPI.get()
            mScannerView.stopCamera()
//            val myDialog = ConfirmationDialog.newInstance(rawResult.text, "cash", 100.0)
//            myDialog.show(supportFragmentManager,"dialog")
        }else{
            desc.text = getString(R.string.activity_qr_error)
            this.onResume()
        }



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        mScannerView = ZXingScannerView(this)
        frame.addView(mScannerView)


    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    fun isValidQrContent(qrContent: String): Boolean{
        val regex = "hackpay:\\/\\/[0-9]+-[0-9a-zA-Z]{8}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{12}"
        return qrContent.matches(Regex(regex))
    }

}
