package com.example.nigeria;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

public class ApiController {

        private List<Topic> topics = new ArrayList<>();
        private List<User> users = new ArrayList<>();

        private User getUserByID(int ID){
            for(int i = 0; i < users.size(); i++){
                if(users.get(i).getUserID() == ID)
                    return users.get(i);
            }
            return null;
        }

        @GetMapping("topics")
        public ResponseEntity<List<Topic>> getTopics(){
            return ResponseEntity.ok(topics);
        }

        @GetMapping("topics/amount")
        public ResponseEntity<Integer> getAmountOfTopics(){
            return ResponseEntity.ok(topics.size());
        }

        @GetMapping("topics/{TopicName}")
        public ResponseEntity<Topic> getTopicByName(@PathVariable("TopicName") String TopicName){
            for(Topic topic : topics){
                if(topic.getTopicName() == TopicName){
                    return ResponseEntity.ok(topic);
                }
            }
            return ResponseEntity.notFound().build();
        }

        @GetMapping("topics/{Index}")
        public ResponseEntity<Topic> getTopicByIndex(@PathVariable("Index") Integer index){
            if(index >= topics.size())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(topics.get(index));
        }

        @PostMapping("topics/create")
        public ResponseEntity<Void> createTopic(@RequestBody Topic newTopic){
            topics.add(newTopic);
            return ResponseEntity.accepted().build();
        }

        @PutMapping("topics/{Index}")
        public ResponseEntity<Void> updateTopic(@PathVariable("Index") Integer index,
                                                @RequestBody Topic updatedTopic){
            if(index >= topics.size())
                return ResponseEntity.notFound().build();
            topics.set(index, updatedTopic);
            return ResponseEntity.accepted().build();
        }

        @DeleteMapping("topics/delete/{Index}")
        public ResponseEntity<Void> deleteTopic(@PathVariable("Index") Integer index){
            if(index >= topics.size())
                return ResponseEntity.notFound().build();
            topics.remove(index);
            return ResponseEntity.accepted().build();
        }

        @DeleteMapping("topics/delete/all")
        public ResponseEntity<Void> deleteAllTopics(){
            topics.clear();
            return ResponseEntity.accepted().build();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        @GetMapping("topics/{Index}/comments")
        public ResponseEntity<List<Commentary>> getCommentsOnTopic(@PathVariable("Index") Integer index){
            if(index >= topics.size())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(topics.get(index).getCommentaries());
        }

        @PostMapping("topics/{Index}/user/{UserID}/comments/create")
        public ResponseEntity<Void> comment(@PathVariable("Index") Integer index,
                                            @PathVariable("UserID") Integer userID,
                                            @RequestBody String commentary){
            if(index >= topics.size())
                return ResponseEntity.notFound().build();
            boolean userExists = false;
            for(int i = 0; i < users.size(); i++){
                if(users.get(i).getUserID() == userID){
                    userExists = true;
                    break;
                }
            }
            if(!userExists){
                users.add(new User("user" + Integer.toString(userID), userID));
            }
            topics.get(index).addCommentary(new Commentary(commentary), getUserByID(userID));
            return ResponseEntity.accepted().build();
        }
        @PutMapping("topics/{Index}/comments/edit/{CommentID}")
        public ResponseEntity<Void> editComment(@PathVariable("Index") Integer index,
                                                @PathVariable("CommentID") Integer commentID,
                                                @RequestBody String newComment){
            if(index >= topics.size())
                return ResponseEntity.notFound().build();
            if(topics.get(index).findCommentByID(commentID) == null)
                return ResponseEntity.notFound().build();
            topics.get(index).findCommentByID(commentID).setText(newComment);
            return ResponseEntity.accepted().build();
        }

        @DeleteMapping("topics/{Index}/comments/delete/{CommentID}")
        public ResponseEntity<Void> deleteComment(@PathVariable("Index") Integer index,
                                                  @PathVariable("CommentID") Integer commentID){
            if(index >= topics.size())
                return ResponseEntity.notFound().build();
            if(topics.get(index).findCommentByID(commentID) == null)
                return ResponseEntity.notFound().build();
            topics.get(index).removeCommentByID(commentID);
            return ResponseEntity.accepted().build();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        @GetMapping("users/{userID}/comments")
        public ResponseEntity<List<Commentary>> getUserComments(@PathVariable("userID") Integer userID){
            User user = getUserByID(userID);
            if(user == null)
                return ResponseEntity.notFound().build();
            if(!user.HasComments())
                return ResponseEntity.noContent().build();
            List<Commentary> commentList = new ArrayList<>();
            for(Topic topic : topics){
                for(Commentary comment : topic.getCommentaries()){
                    for(int commentID : user.getCommentsID()){
                        if(commentID == comment.getID())
                            commentList.add(comment);
                    }
                }
            }
            return ResponseEntity.ok(commentList);
        }

        @GetMapping("users/{userID}/comments/topic/{Index}")
        public ResponseEntity<List<Commentary>> getUserCommentsOnTopic(@PathVariable("userID") Integer userID,
                                                                       @PathVariable("Index") Integer index){
            User user = getUserByID(userID);
            if(user == null)
                return ResponseEntity.notFound().build();
            if(!user.HasComments())
                return ResponseEntity.noContent().build();
            List<Commentary> commentList = new ArrayList<>();
            for(Commentary comment : topics.get(index).getCommentaries()){
                for(int commentID : user.getCommentsID())
                    if(commentID == comment.getID())
                        commentList.add(comment);
            }
            return ResponseEntity.ok(commentList);
        }
}
