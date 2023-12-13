package com.hien.ketnoiviet.model;

import java.io.Serializable;

public class follower implements Serializable {
    private  int id ;
   private String users ;
   private String userFollow ;

    public follower() {
    }

    public follower(int id, String users, String userFollow) {
        this.id = id;
        this.users = users;
        this.userFollow = userFollow;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public void setUserFollow(String userFollow) {
        this.userFollow = userFollow;
    }

    public int getId() {
        return id;
    }

    public String getUsers() {
        return users;
    }

    public String getUserFollow() {
        return "0"+userFollow;
    }

    @Override
    public String toString() {
        return "follower{" +
                "id=" + id +
                ", users='" + users + '\'' +
                ", userFollow='" + userFollow + '\'' +
                '}';
    }
}
