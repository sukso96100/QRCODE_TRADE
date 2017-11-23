package xyz.youngbin.hackpay.ui.activities

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.zxing.Result

import kotlinx.android.synthetic.main.activity_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import xyz.youngbin.hackpay.R
import xyz.youngbin.hackpay.ui.dialogs.ConfirmationDialog

class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, DialogInterface.OnDismissListener{


    override fun onDismiss(dialog: DialogInterface?) {
        mScannerView.startCamera()
    }

    private lateinit var mScannerView: ZXingScannerView

    override fun handleResult(rawResult: Result?) {
        mScannerView.stopCamera()
        Toast.makeText(this, rawResult!!.text, Toast.LENGTH_LONG).show()
        val myDialog = ConfirmationDialog.newInstance(rawResult.text, "cash", 100.0)
        myDialog.show(supportFragmentManager,"dialog")

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

}
