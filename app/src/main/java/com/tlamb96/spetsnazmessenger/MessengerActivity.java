package com.tlamb96.spetsnazmessenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.tlamb96.spetsnazmessenger.adapter.MessageListAdapter;
import com.tlamb96.spetsnazmessenger.pojo.Message;

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
        mMessages = new ArrayList<Message>() {{
            add(new Message("Bob", "hi", "the time 6", true));
            add(new Message("Tyler", "hey there", "the time 7", false));
        }};

        // Setup the recycler view that displays the individual text messages.
        mMessagesRecyclerView = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageListAdapter = new MessageListAdapter(this, mMessages);
        mMessagesRecyclerView.setAdapter(mMessageListAdapter);
        mMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Called when the send button is pressed.
    public void onSendMessage(View view) {
        String textMessage;
        EditText messageTextbox = (EditText) findViewById(R.id.edittext_chatbox);
        if(TextUtils.isEmpty(textMessage = messageTextbox.getText().toString())) {
            return;
        }
        mMessages.add(new Message(getResources().getString(R.string.user), textMessage, "timestamp", false));
        mMessageListAdapter.notifyDataSetChanged();
        messageTextbox.setText("");
    }
}
