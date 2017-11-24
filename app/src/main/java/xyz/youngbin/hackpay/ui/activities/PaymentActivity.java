package xyz.youngbin.hackpay.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by ping2 on 2017-11-23.
 */

public class PaymentActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(this, PincodeActivity.class);
        intent.putExtra("mode","check");
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(PaymentActivity.this);
                dialog.setTitle("비밀번호 5회 초과 오류 ");
                TextView textView=new TextView(PaymentActivity.this);
                textView.setText("비밀번호 오류를 5회 초과하여 결제를 취소합니다.");
                textView.setPadding(25,50,0,0);
                dialog.setView(textView);
                dialog.setCancelable(false);
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialog.show();
                break;
        }
    }

}
