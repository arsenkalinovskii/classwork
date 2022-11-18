package com.example.nigeria;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.ArrayList;

public class Topic {
    private String TopicName;
    private List<Commentary> Commentaries = new ArrayList<>();

    public Topic(List<Commentary> commentaries) {
        Commentaries = commentaries;
    }

    public Topic(String topicName) {
        TopicName = topicName;
    }
    public Topic(String topicName, List<Commentary> commentaries){
        Commentaries = commentaries;
        TopicName = topicName;
    }

    public List<Commentary> getCommentaries() {
        return Commentaries;
    }
    public String getTopicName() {
        return TopicName;
    }
    public void setCommentaries(List<Commentary> commentaries) {
        Commentaries = commentaries;
    }
    public void setTopicName(String topicName) {
        TopicName = topicName;
    }
    public void addCommentary(Commentary comment, User author){
        Commentaries.add(comment);
        author.addCommentID(comment.getID());
    }

    public void removeCommentByContent(Commentary comment){
        for(int i = 0; i < Commentaries.size(); i++){
            if(Commentaries.get(i).equals(comment)) {
                Commentaries.remove(i);
            }
        }
    }
    public void removeCommentByID(int ID){
        for(int i = 0; i < Commentaries.size(); i++){
            if(Commentaries.get(i).getID() == ID) {
                Commentaries.remove(i);
                break;
            }
        }
    }

    public Commentary findCommentByID(int ID){
        Commentary result = null;
        for(int i = 0; i < Commentaries.size(); i++){
            if(Commentaries.get(i).getID() == ID){
                result = Commentaries.get(i);
                break;
            }
        }
        return result;
    }


}
