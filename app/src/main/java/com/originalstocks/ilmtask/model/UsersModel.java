package com.originalstocks.ilmtask.model;

public class UsersModel {

    private String login;
    private String userImageLink;
    private String userURL;
    private String userType;
    private int pos;

    public UsersModel(int pos) {
        this.pos = pos;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserImageLink() {
        return userImageLink;
    }

    public void setUserImageLink(String userImageLink) {
        this.userImageLink = userImageLink;
    }

    public String getUserURL() {
        return userURL;
    }

    public void setUserURL(String userURL) {
        this.userURL = userURL;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
