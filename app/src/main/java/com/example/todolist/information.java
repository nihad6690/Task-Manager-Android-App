package com.example.todolist;


public class information {




    public String title;
    public String description;
    public String taskId;
    public String userId;



    public information(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public information(String title, String description, String taskId) {
        this.title = title;
        this.description = description;
        this.taskId = taskId;
    }
    public information(String title, String description, String taskId, String userId) {
        this.title = title;
        this.description = description;
        this.taskId = taskId;
        this.userId = userId;
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
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }




    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
