package com.tlamb96.spetsnazmessenger.pojo;

/**
 * Created by Tyler on 4/11/18.
 */

public class Message {
    private String mMessage, mUser, mTimestamp;
    private boolean mIsReceived;

    public Message(String user, String message, String timestamp, boolean isReceived) {
        mUser = user;
        mMessage = message;
        mTimestamp = timestamp;
        mIsReceived = isReceived;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getUser() {
        return mUser;
    }

    /**
     * Gets the ISO8601 timestamp.
     * @return The ISO8601 timestamp that describes when the message was sent.
     */
    public String getTimestamp() {
        return mTimestamp;
    }

    public boolean isReceived() {
        return mIsReceived;
    }
}
