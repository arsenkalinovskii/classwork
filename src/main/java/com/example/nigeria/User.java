package com.example.nigeria;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static int globalID = 0;

    private int userID;
    private String username;

    private List<Integer> commentsID = new ArrayList<>();
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }

    public int getGlobalID(){
        return globalID;
    }
    public User(String username) {
        globalID++;
        this.userID = globalID;
        this.username = username;
    }
    public User(String username, int newID){
        globalID++;
        this.userID = newID;
        this.username = username;
    }

    public boolean equals(User other){
        if(userID == other.getUserID())
            return true;
        return false;
    }
    public boolean HasComments(){
        return commentsID.size() > 0;
    }

    public void addCommentID(int ID){
        commentsID.add(ID);
    }

    public List<Integer> getCommentsID(){
        return commentsID;
    }
}
