package xyz.youngbin.hackpay.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import network.HPAPI
import xyz.youngbin.hackpay.R
import xyz.youngbin.hackpay.ui.activities.seller.SellerProductListActivity
import xyz.youngbin.hackpay.ui.activities.seller.SellerRequestPaymentActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        creditsBalanceLabel.text = "${intent.getIntExtra("balance", 0)} KRW"

        payment.setOnClickListener{
            val permissions = listOf(android.Manifest.permission.CAMERA)
            val listener = object : PermissionListener {
                override fun onPermissionGranted() {
                    startActivity(Intent(this@MainActivity, ScannerActivity::class.java))
                }
                override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                    Toast.makeText(this@MainActivity, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            TedPermission.with(this)
                    .setPermissionListener(listener)
                    .setDeniedMessage("시스템 설정에서 어플리케이션 권한을 허용해주세요.")
                    .setPermissions(*permissions.toTypedArray())
                    .check()
        }
        credits.setOnClickListener {
            startActivity(Intent(this, CreditsAndHistoryActivity::class.java))
        }

        if (HPAPI.seller_id == null) {
            store.visibility = View.GONE
        }else {
            store.setOnClickListener {
                startActivity(Intent(this, SellerProductListActivity::class.java))
            }
            storeReqProduct.setOnClickListener {
                startActivity(Intent(this, SellerRequestPaymentActivity::class.java))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
