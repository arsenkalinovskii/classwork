package com.example.nigeria;

public class Commentary {
    private String text;

    private static int ID = 0;


    public Commentary() {;
        ID++;
    }

    public Commentary(String text) {
        this.text = text;;
        ID++;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public int getID(){
        return ID;
    }
    public boolean equals(Commentary commentary){
        if(text.equals(commentary.getText()))
            return true;
        return false;
    }
}
