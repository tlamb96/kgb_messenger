package com.tlamb96.kgbmessenger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import net.danlew.android.joda.JodaTimeAndroid;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Exit if the user.home property for the app does not equal "Russia". The user.home
        // property has nothing to do with the phones physical location, but I needed a check that
        // would always fail. This should probably be patched/removed to solve this challenge.
        String userHome = System.getProperty("user.home");
        if(userHome == null || userHome.isEmpty() || !userHome.equals("Russia")) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Integrity Error");
            alertDialog.setMessage("This app can only run on Russian devices.");
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "EXIT",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.show();

            // Center the "EXIT" button in a slightly hacky way.
            final Button button = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
            LinearLayout parent = (LinearLayout) button.getParent();
            parent.setGravity(Gravity.CENTER_HORIZONTAL);
            View leftSpacer = parent.getChildAt(1);
            leftSpacer.setVisibility(View.GONE);
        }

        JodaTimeAndroid.init(this);
    }

    public void onOpenMessenger(View view) {
        Intent intent = new Intent(this, MessengerActivity.class);
        startActivity(intent);
    }
}
