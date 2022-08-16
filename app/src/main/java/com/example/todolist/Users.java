package com.example.todolist;



public class Users {
    String username;
    String email;
    String password;
    String title;
    String description;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userId;

    public Users(String username, String email, String password, String title, String description, String userId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    public Users(){}

    //sign up constructer
    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
