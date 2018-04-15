package com.tlamb96.kgbmessenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.tlamb96.kgbmessenger.adapter.MessageListAdapter;
import com.tlamb96.kgbmessenger.pojo.Message;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";

    private RecyclerView mMessagesRecyclerView;
    private MessageListAdapter mMessageListAdapter;
    private List<Message> mMessages;

    // "Boris, give me the password"
    private String mAskBorris = "V@]EAASB\u0012WZF\u0012e,a$7(&am2(3.\u0003";
    // Stores the user's correct input for the ask Boris question.
    private String mUserAskBoris;

    // "May I *PLEASE* have the password?"
    private String mAskBorrisNicely = "\u0000\u0064\u0073\u006c\u0070\u007d\u006f\u0051\u0000\u0020\u0064\u006b\u0073\u0024\u007c\u004d\u0000\u0068\u0020\u002b\u0041\u0059\u0051\u0067\u0000\u0050\u002a\u0021\u004d\u0024\u0067\u0051\u0000";
    // Stores the user's correct input for the ask Boris nicely question.
    private String mUserAskBorisNicely;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        // TODO: this should come from a database or xml file.
        // Populate some messages.
        final String katya = getResources().getString(R.string.katya),
                archer = getResources().getString(R.string.user);
        mMessages = new ArrayList<Message>() {{
            add(new Message(R.string.katya, "Archer, you up?", "2:20 am", true));
            add(new Message(R.string.user, "no", "2:22 am", false));
            add(new Message(R.string.nikolai, "Omg Katya you're being so transparent...", "7:16 am", true));
            add(new Message(R.string.crenshaw, "LOL you should deport her", "7:28 am", true));
            add(new Message(R.string.user, "Why am I in this gc again?", "7:48 am", false));
            add(new Message(R.string.katya, "DEPORT me!? Where tf would you send me!?? I'm already stuck living in Russia", "8:02 am", true));
            add(new Message(R.string.boris, "Pls don't deport me", "8:05 am", true));
            add(new Message(R.string.katya, "Boris no one is talking about you", "8:06 am", true));
            add(new Message(R.string.nikolai, "Omg he's such a moron", "8:10 am", true));
            add(new Message(R.string.crenshaw, "ikr", "8:11 am", true));
            add(new Message(R.string.nikolai, "Remember that time he gave away the password to all KGB systems?", "8:12 am", true));
            add(new Message(R.string.crenshaw, "Yeah, all they had to do was ask for it", "8:13 am", true));
            add(new Message(R.string.katya, "You're joking, right? No one is that dumb", "8:13 am", true));
            add(new Message(R.string.crenshaw, "I'm 100% serious", "8:13 am", true));
            add(new Message(R.string.boris, "Well that's not all they had to do", "8:15 am", true));
            add(new Message(R.string.katya, "Wait, why do all KGB systems have the same password?", "9:20 am", true));
            add(new Message(R.string.nikolai, "We got tired of writing them down on sticky notes so we held a meeting and agreed on a password for the entire department", "9:22 am", true));
            add(new Message(R.string.crenshaw, "Idk why we didn't think of this solution earlier", "9:25 am", true));
            add(new Message(R.string.katya, "Does Boris know the password?", "9:26 am", true));
            add(new Message(R.string.nikolai, "Nah, he only has the password for his personal computer which is different than the dept's password", "9:27 am", true));
            add(new Message(R.string.crenshaw, "You thought we'd tell him again? If he told someone we would have to hold another dept meeting to come up with a new one", "9:28 am", true));
            add(new Message(R.string.nikolai, "It took us three hours to agree on one last time", "9:27 am", true));
        }};

        // Setup the recycler view that displays the individual text messages.
        mMessagesRecyclerView = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageListAdapter = new MessageListAdapter(this, mMessages);
        mMessagesRecyclerView.setAdapter(mMessageListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mMessagesRecyclerView.setLayoutManager(linearLayoutManager);
        mMessagesRecyclerView.setNestedScrollingEnabled(false);
    }

    // The hash function that checks if the user asked Boris for the password.
    private String easyHash(String s) {
        char tmp;
        char[] input = s.toCharArray();
        for(int i=0; i<input.length/2; i++) {
            // Swap and XOR the chars by different values.
            tmp = input[i];
            input[i] = (char) (input[input.length-i-1] ^ 0x32);
            input[input.length-i-1] = (char) (tmp ^ 0x41);
        }
        return new String(input);
    }

    // The hash function that checks if the user asked nicely for the password.
    private String hardHash(String s) {
        int tmp;
        char tmpChar;
        char[] input = s.toCharArray();
        // XOR each char by a bit shifted version of itself. The shift amount is the iterator mod 8.
        for(int i=0; i<input.length; i++) {
            tmp = input[i] >> (i%8);
            input[i] = (char) (input[i] ^ tmp);
        }
        // Reverse the array.
        for(int i=0; i<input.length/2; i++) {
            // Swap and XOR the chars by different values.
            tmpChar = input[i];
            input[i] = input[input.length-i-1];
            input[input.length-i-1] = tmpChar;
        }
        return new String(input);
    }

    private String generateFlag() {
        String flag;

        // Make sure they asked Boris for the passwords correctly.
        if(mUserAskBoris == null || mUserAskBorisNicely == null) {
            return "Nice try but you're not that slick!";
        }

        // Get the string "password" from the user entered text.
        char[] flag1 = mUserAskBoris.substring(19).toCharArray();
        // XOR it to "p455w0rd".
        flag1[1] ^= 0x55;
        flag1[2] ^= 0x46;
        flag1[3] ^= 0x46;
        flag1[5] ^= 0x5f;
        Log.i(TAG, "flag: "+new String(flag1));

        // Get the string "PLEASE" from the user entered text.
        char[] flag2 = mUserAskBorisNicely.substring(7,13).toCharArray();
        // XOR it to "P134SE".
        flag2[1] ^= 0x7d;
        flag2[2] ^= 0x76;
        flag2[3] ^= 0x75;

        return new String(flag1) + "_" + new String(flag2);
    }

    private String getCurrentHoursMinutes() {
        DateTime dateTime = new DateTime();
        return dateTime.toString("hh:mm a");
    }

    // Called when the send button is pressed.
    public void onSendMessage(View view) {
        String textMessage;
        EditText messageTextbox = (EditText) findViewById(R.id.edittext_chatbox);
        if(TextUtils.isEmpty(textMessage = messageTextbox.getText().toString())) {
            return;
        }
        mMessages.add(new Message(R.string.user, textMessage, getCurrentHoursMinutes(), false));
        mMessageListAdapter.notifyDataSetChanged();

        // If the user asked Boris for the password, send a message from Boris asking
        if(easyHash(textMessage.toString()).equals(mAskBorris)) {
            Log.d(TAG, "Successfully asked Boris for the password.");
            mUserAskBoris = textMessage.toString();
            mMessages.add(new Message(R.string.boris, "Only if you ask nicely", getCurrentHoursMinutes(), true));
            mMessageListAdapter.notifyDataSetChanged();
            // I would want to block here for a second to make it seem like he's texting but we
            // could accidentally block UI thread for too long and get killed by watchdog. I'm
            // too lazy to set it up in another thread.
        }

        if(hardHash(textMessage.toString()).equals(mAskBorrisNicely)) {
            Log.d(TAG, "Successfully asked Boris nicely for the password.");
            mUserAskBorisNicely = textMessage.toString();
            // Print out the flag: FLAG{p455w0rd_P134SE}
            mMessages.add(new Message(R.string.boris, "Wow, no one has ever been so nice to me! Here you go friend: FLAG{"+generateFlag()+"}", getCurrentHoursMinutes(), true));
            mMessageListAdapter.notifyDataSetChanged();
        }

        // Scroll to bottom and clear textbox.
        mMessagesRecyclerView.smoothScrollToPosition(mMessagesRecyclerView.getAdapter().getItemCount()-1);
        messageTextbox.setText("");
    }
}
