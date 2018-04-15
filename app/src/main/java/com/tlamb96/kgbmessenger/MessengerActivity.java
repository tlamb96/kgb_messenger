package com.tlamb96.kgbmessenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.tlamb96.kgbmessenger.adapter.MessageListAdapter;
import com.tlamb96.kgbmessenger.pojo.Message;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MessengerActivity extends AppCompatActivity {
    private RecyclerView mMessagesRecyclerView;
    private MessageListAdapter mMessageListAdapter;
    private List<Message> mMessages;

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

    public String getCurrentHoursMinutes() {
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

        // Scroll to bottom and clear textbox.
        mMessagesRecyclerView.smoothScrollToPosition(mMessagesRecyclerView.getAdapter().getItemCount()-1);
        messageTextbox.setText("");
    }
}
