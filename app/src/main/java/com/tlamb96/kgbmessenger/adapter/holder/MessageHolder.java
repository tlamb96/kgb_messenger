package com.tlamb96.kgbmessenger.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlamb96.kgbmessenger.pojo.Message;
import com.tlamb96.kgbmessenger.R;

/**
 * Created by Tyler on 4/11/18.
 */

public class MessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    ImageView profileImage;

    public MessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        nameText = (TextView) itemView.findViewById(R.id.text_message_name);
        profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
    }

    public void bind(Message message) {
        messageText.setText(message.getMessage());
        timeText.setText(message.getTimestamp());

        if(message.isReceived()) {
            nameText.setText(message.getUser());
            // Insert the profile image from the URL into the ImageView.
            //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }
}
