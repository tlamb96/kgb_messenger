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
            add(new Message(R.string.katya, "Hey Archer", "1:05 pm", true));
            add(new Message(R.string.katya, "What are you up to?", "1:05 pm", true));
            add(new Message(R.string.user, "Mind your own goddamn business", "1:10 pm", false));
        }};

        // Setup the recycler view that displays the individual text messages.
        mMessagesRecyclerView = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageListAdapter = new MessageListAdapter(this, mMessages);
        mMessagesRecyclerView.setAdapter(mMessageListAdapter);
        mMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public String getCurrentHoursMinutes() {
        DateTime dateTime = new DateTime();
        return String.format("%02d:%d %s",
                // Probably incorrect because JODA Android is annoying compared to the normal JODA.
                dateTime.getHourOfDay() == 12 ? 12 : dateTime.getHourOfDay()%12,
                dateTime.getMinuteOfHour(),
                dateTime.getHourOfDay() <= 12 ? "am" :"pm"
        );
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
        messageTextbox.setText("");
    }
}
