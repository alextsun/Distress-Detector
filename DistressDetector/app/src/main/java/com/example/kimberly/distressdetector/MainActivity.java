package com.example.kimberly.distressdetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.kimberly.distressdetector.util.OnScreenActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleClick(View v) {
        EditText et1 = (EditText) findViewById(R.id.editText);
        int age = Integer.parseInt(et1.getText().toString());

        EditText et2 = (EditText) findViewById(R.id.editText2);
        int phNum = Integer.parseInt(et2.getText().toString());

        EditText et3 = (EditText) findViewById(R.id.editText3);
        String name = (et3.getText().toString());

        Intent transfer = new Intent(this, OnScreenActivity.class);
        transfer.putExtra("age", String.valueOf(age));
        transfer.putExtra("phNum", String.valueOf(phNum));
        transfer.putExtra("name", name);
        startActivity(transfer);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
