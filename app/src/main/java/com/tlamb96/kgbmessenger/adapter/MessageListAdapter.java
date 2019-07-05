package com.tlamb96.kgbmessenger.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlamb96.kgbmessenger.adapter.holder.MessageHolder;
import com.tlamb96.kgbmessenger.pojo.Message;
import com.tlamb96.kgbmessenger.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 4/11/18.
 */

public class MessageListAdapter extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_MESSAGE_SENT = 1;
    public static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private static final String TAG = "MessageListAdapter";
    private Context mContext;
    private List<Message> mMessageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        mContext = context;
        mMessageList = messageList == null ? new ArrayList<Message>() : messageList;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new MessageHolder(view, mContext);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new MessageHolder(view, mContext);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) mMessageList.get(position);
        if (message == null) {
            Log.e(TAG, String.format("message was not found at position %d", position));
            return;
        }
        ((MessageHolder) holder).bind(message);
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message message = (Message) mMessageList.get(position);

        if (message.isReceived()) {
            Log.i(TAG, "message type is received");
            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else {
            Log.i(TAG, "message type is sent");
            return VIEW_TYPE_MESSAGE_SENT;
        }
    }
}
