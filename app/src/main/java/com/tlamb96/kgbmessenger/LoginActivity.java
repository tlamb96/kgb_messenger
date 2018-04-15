package com.tlamb96.kgbmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private MessageDigest mMessageDigest;
    private String mUsersEnteredUsername;
    private String mUsersEnteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setup MD5 hashing.
        try {
            mMessageDigest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, "MD5 algorithm not found.");
        }
    }

    private void showFlag() {
        // mUsersEnteredUsername == "codenameduchess"
        // mUsersEnteredPassword == "guest"
        // FLAG{G00G13_PR0}
        char[] flag = {0x28, 0x57, 0x44, 0x29, 0x54, 0x50, 0x3a, 0x23, 0x3f, 0x54};
        flag[0] ^= mUsersEnteredUsername.charAt(1); // 0x28 ^ "o"
        flag[1] ^= mUsersEnteredPassword.charAt(0); // 0x57 ^ "g"
        flag[2] ^= mUsersEnteredPassword.charAt(4); // 0x44 ^ "t"
        flag[3] ^= mUsersEnteredUsername.charAt(4); // 0x29 ^ "n"
        flag[4] ^= mUsersEnteredUsername.charAt(7); // 0x54 ^ "e"
        flag[5] ^= mUsersEnteredUsername.charAt(0); // 0x50 ^ "c"
        flag[6] ^= mUsersEnteredPassword.charAt(2); // 0x3a ^ "e"
        flag[7] ^= mUsersEnteredPassword.charAt(3); // 0x23 ^ "s"
        flag[8] ^= mUsersEnteredUsername.charAt(6); // 0x3f ^ "m"
        flag[9] ^= mUsersEnteredUsername.charAt(8); // 0x54 ^ "d"
        Toast.makeText(this, "FLAG{"+new String(flag)+"}", Toast.LENGTH_LONG).show();
    }

    private boolean checkPassword() {
        byte[] userPasswordHashBytes = mMessageDigest.digest(mUsersEnteredPassword.getBytes());
        String userPasswordHash = "";
        for(byte userPasswordHashByte : userPasswordHashBytes) {
            userPasswordHash += String.format("%x", userPasswordHashByte);
        }
        return userPasswordHash.equals(getResources().getString(R.string.password));
    }

    public void onLogin(View view) {
        EditText usernameEditText = findViewById(R.id.login_username);
        EditText passwordEditText = findViewById(R.id.login_password);
        mUsersEnteredUsername = usernameEditText.getText().toString();
        mUsersEnteredPassword = passwordEditText.getText().toString();

        // Check that text boxes aren't empty.
        if(mUsersEnteredUsername == null || mUsersEnteredPassword == null || mUsersEnteredUsername.isEmpty() || mUsersEnteredPassword.isEmpty()) {
            return;
        }

        // Check if the user is Archer.
        if(!mUsersEnteredUsername.equals(getResources().getString(R.string.username))) {
            Toast.makeText(this, "User not recognized.", Toast.LENGTH_SHORT).show();
            usernameEditText.setText("");
            passwordEditText.setText("");
        }

        // Check if the password is "guest". This is a simple recon challenge because it's the
        // password to ISIS's network in the show. It can be solved by searching "archer password"
        // on Google and watching the first video: https://youtu.be/UduILWi2p6s
        else if(!checkPassword()) {
            Toast.makeText(this, "Incorrect password.", Toast.LENGTH_SHORT).show();
            usernameEditText.setText("");
            passwordEditText.setText("");
        }
        else {
            showFlag();
            // Go to the messenger screen.
            Intent intent = new Intent(this, MessengerActivity.class);
            startActivity(intent);
        }
    }
}
