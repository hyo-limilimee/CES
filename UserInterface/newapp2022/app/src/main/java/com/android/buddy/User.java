package com.android.buddy;

class User {

    String friendEmail, friendName;

    public User(){}

    public User(String friendEmail, String friendName) {
        this.friendEmail = friendEmail;
        this.friendName = friendName;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
