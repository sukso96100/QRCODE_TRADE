package xyz.youngbin.hackpay.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ping2 on 2017-11-23.
 */

public class PaymentActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(this, PincodeActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 0) return;

        switch (resultCode){
            case RESULT_OK: //pincode 일치
                //결제처리
                Intent intent=new Intent(this,ResultActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1: //pincode 5회 실패
                break;
        }
    }

}
