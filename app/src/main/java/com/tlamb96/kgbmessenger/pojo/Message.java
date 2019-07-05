package com.tlamb96.kgbmessenger.pojo;

/**
 * Created by Tyler on 4/11/18.
 */

public class Message {
    private int mUser; // The id of the strings.xml entry that contains the user's name.
    private String mMessage, mTimestamp;
    private boolean mIsReceived;

    public Message(int user, String message, String timestamp, boolean isReceived) {
        mUser = user;
        mMessage = message;
        mTimestamp = timestamp;
        mIsReceived = isReceived;
    }

    public String getMessage() {
        return mMessage;
    }

    public int getUser() {
        return mUser;
    }

    /**
     * Gets the ISO8601 timestamp.
     *
     * @return The ISO8601 timestamp that describes when the message was sent.
     */
    public String getTimestamp() {
        return mTimestamp;
    }

    public boolean isReceived() {
        return mIsReceived;
    }
}
