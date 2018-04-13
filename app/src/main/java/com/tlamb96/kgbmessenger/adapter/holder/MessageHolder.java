package com.tlamb96.kgbmessenger.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlamb96.kgbmessenger.R;
import com.tlamb96.kgbmessenger.pojo.Message;

/**
 * Created by Tyler on 4/11/18.
 */

public class MessageHolder extends RecyclerView.ViewHolder {
    TextView mMessageText, mTimeText, mNameText;
    ImageView mProfileImage;
    Context mContext;

    public MessageHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        mMessageText = (TextView) itemView.findViewById(R.id.text_message_body);
        mTimeText = (TextView) itemView.findViewById(R.id.text_message_time);
        mNameText = (TextView) itemView.findViewById(R.id.text_message_name);
        mProfileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
    }

    public void bind(Message message) {
        mMessageText.setText(message.getMessage());
        mTimeText.setText(message.getTimestamp());

        if(message.isReceived()) {
            mNameText.setText(message.getUser());
            switch (message.getUser()) {
                case R.string.katya:
                    mProfileImage.setImageResource(R.drawable.katyakazanova2);
                    break;
                case R.string.nikolai:
                    mProfileImage.setImageResource(R.drawable.nikolai_jakov);
                    break;
                case R.string.crenshaw:
                    mProfileImage.setImageResource(R.drawable.crenshaw);
                    break;
            }
        }
    }
}
