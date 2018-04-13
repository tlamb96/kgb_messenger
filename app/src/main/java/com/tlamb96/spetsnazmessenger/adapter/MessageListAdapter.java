package com.tlamb96.spetsnazmessenger.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlamb96.spetsnazmessenger.R;
import com.tlamb96.spetsnazmessenger.adapter.holder.MessageHolder;
import com.tlamb96.spetsnazmessenger.pojo.Message;

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
            return new MessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new MessageHolder(view);
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
        if(message == null) {
            Log.e(TAG, String.format("message was not found at position %d", position));
            return;
        }
        ((MessageHolder) holder).bind(message);
        /*
        Log.d(TAG, "onBindViewHolder "+mMessageList.get(position).getMessage());
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((MessageHolder) holder).bind(message, true);
            case VIEW_TYPE_MESSAGE_SENT:
                ((MessageHolder) holder).bind(message);
                break;
        }
        */
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

    /*
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText("Time here 2");
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText("time here 3");

            nameText.setText(message.getUser());

            // Insert the profile image from the URL into the ImageView.
            //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }
    */
}
