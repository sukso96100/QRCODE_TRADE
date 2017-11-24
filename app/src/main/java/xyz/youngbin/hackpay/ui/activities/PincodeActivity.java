package xyz.youngbin.hackpay.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

import org.w3c.dom.Text;

import xyz.youngbin.hackpay.R;

/**
 * Created by ping2 on 2017-11-23.
 */

public class PincodeActivity extends Activity {

    public static final String TAG = "PinLockView";

    private TextView header;
    private TextView comment;
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private int attemp=1;
    private String mode;
    String inputPincode="";

    public boolean checkUserPincode(String pincode){
        if(pincode.equals("11111")) {
            //pincode 일치
            return true;
        }
        else{
            //불일치
            return false;
        }
    }

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin)
        {
            if(mode.equals("check")) {
                if (checkUserPincode(pin)) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    if (attemp == 5) {
                        setResult(1);
                        finish();
                    }
                    comment.setText(R.string.incorrect_pincode);
                    attemp++;
                    mPinLockView.resetPinLockView();
                }
                Log.d(TAG, "Pin complete: " + pin);
            }
            else{  //setting
                if(attemp==1){
                    comment.setText(R.string.reinput_pincode);
                    attemp++;
                    inputPincode=pin;
                    mPinLockView.resetPinLockView();
                }
                else{ //비밀번호 재입력했을때
                    if(pin.equals(inputPincode)){
                        //서버에 pin번호 전송
                        finish();
                    }
                    else{
                        comment.setText(R.string.incorrect_pincode);
                        attemp=1;
                        mPinLockView.resetPinLockView();
                    }
                }
            }
        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        header=(TextView) findViewById(R.id.pincode_header);
        comment=(TextView) findViewById(R.id.pincode_comment);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        mPinLockView.setPinLength(5);

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

        Intent intent=getIntent();
        mode=intent.getStringExtra("mode");
        if(mode.equals("check")){
            header.setText("Pin 확인");
        }
        else{
            header.setText("Pin 설정");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home: onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
