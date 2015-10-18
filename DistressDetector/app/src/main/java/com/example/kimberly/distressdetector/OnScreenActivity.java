package com.example.kimberly.distressdetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kimberly.distressdetector.R;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandException;
import com.microsoft.band.BandIOException;
import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.HeartRateConsentListener;

public class OnScreenActivity extends AppCompatActivity implements HeartRateConsentListener {

    private int heartRate = 0;
    private BandClient client = null;
    private BandHeartRateEventListener mHeartRateEventListener = new BandHeartRateEventListener() {
        @Override
        public void onBandHeartRateChanged(final BandHeartRateEvent event) {
            heartRate = event.getHeartRate();
            Log.e("heart rate", String.valueOf(heartRate));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in = getIntent();

        String name = in.getStringExtra("name");
        int phNum = Integer.parseInt(in.getStringExtra("phNum"));
        int age = Integer.parseInt(in.getStringExtra("age"));

        Log.e("message", "Name: " + name + " phNum: " + phNum + " age: " + age);
        if(client.getSensorManager().getCurrentHeartRateConsent() !=
                UserConsent.GRANTED) {
            // user hasn’t consented, request consent
            // the calling class is an Activity and implements
            // HeartRateConsentListener
            client.getSensorManager().requestHeartRateConsent(this,
                    this);
        }
        try {
            // register the listener
            client.getSensorManager().registerHeartRateEventListener(
                    mHeartRateEventListener);
        } catch(BandException ex) {
            // handle BandException
        }

        setContentView(R.layout.activity_on_screen);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (client!=null) {
            try {
                client.getSensorManager().unregisterHeartRateEventListener(mHeartRateEventListener);
            } catch (BandIOException e) {
                Log.e("msft band io error", e.getMessage());
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (client != null) {
            try {
                client.disconnect().await();
            } catch (InterruptedException e) {
                // Do nothing as this is happening during destroy
            } catch (BandException e) {
                // Do nothing as this is happening during destroy
            }
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_on_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void userAccepted(boolean consent) {
        TextView editText = (TextView) findViewById(R.id.textView4);
        if (!consent) {
            editText.setText("The Microsoft Band needs permission to take your heart rate for the app to work");
        } else {
            editText.setText("We\'re monitoring!");
        }
    }
}