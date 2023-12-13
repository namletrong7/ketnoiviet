package com.hien.ketnoiviet.model;

public class notification {
    String idNotifi;
    int idPost, type;
    String nameUser, avatarUser;
    String hasRead, idReadNotifi, timeNotifi;

    public notification() {
    }

    public notification(String idNotifi, int idPost, int type, String nameUser, String avatarUser, String hasRead, String idReadNotifi, String timeNotifi) {
        this.idNotifi = idNotifi;
        this.idPost = idPost;
        this.type = type;
        this.nameUser = nameUser;
        this.avatarUser = avatarUser;
        this.hasRead = hasRead;
        this.idReadNotifi = idReadNotifi;
        this.timeNotifi = timeNotifi;
    }

    public String getIdNotifi() {
        return idNotifi;
    }

    public void setIdNotifi(String idNotifi) {
        this.idNotifi = idNotifi;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getAvatarUser() {
        return avatarUser;
    }

    public void setAvatarUser(String avatarUser) {
        this.avatarUser = avatarUser;
    }

    public String getHasRead() {
        return hasRead;
    }

    public void setHasRead(String hasRead) {
        this.hasRead = hasRead;
    }

    public String getIdReadNotifi() {
        return idReadNotifi;
    }

    public void setIdReadNotifi(String idReadNotifi) {
        this.idReadNotifi = idReadNotifi;
    }

    public String getTimeNotifi() {
        return timeNotifi;
    }

    public void setTimeNotifi(String timeNotifi) {
        this.timeNotifi = timeNotifi;
    }
}
