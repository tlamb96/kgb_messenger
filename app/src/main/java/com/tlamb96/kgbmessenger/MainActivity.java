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

        String userHome = System.getProperty("user.home");
        String user = System.getenv("USER");

        // Exit if the user.home property for the app does not equal "Russia". The user.home
        // property has nothing to do with the phones physical location, but I needed a check that
        // would always fail. This should probably be patched/removed to solve this challenge.
        if (userHome == null || userHome.isEmpty() || !userHome.equals("Russia")) {
            showExitAlertDialog("Integrity Error", "This app can only run on Russian devices.");
        }
        // Checks an environment variable to see who the user is. Again, this isn't really a thing
        // in Android but it's something that some people might skip over expecting the strings
        // to be "Sterling Archer" when it's really the Base 64 encoded flag: FLAG{57ERL1NG_4RCH3R}.
        else if (user == null || user.isEmpty() || !user.equals(getResources().getString(R.string.User))) {
            showExitAlertDialog("Integrity Error", "Must be on the user whitelist.");
        } else {
            JodaTimeAndroid.init(this);
            // Go to the login screen.
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void showExitAlertDialog(String title, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
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

    /**
     * Launch the home screen when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        Intent homeScreen = new Intent(Intent.ACTION_MAIN);
        homeScreen.addCategory(Intent.CATEGORY_HOME);
        homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeScreen);
    }
}
