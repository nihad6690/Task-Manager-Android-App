package com.example.todolist;

import android.app.Application;
import android.view.View;

public class Applicationclass extends Application {
    tasks_recyclerViewAdapter adapter;
    public boolean notUsedDatabaseContent = true;

    public boolean isNotUsedDatabaseContent() {
        return notUsedDatabaseContent;
    }

    public void setNotUsedDatabaseContent(boolean notUsedDatabaseContent) {
        this.notUsedDatabaseContent = notUsedDatabaseContent;
    }

    public tasks_recyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(tasks_recyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    public Applicationclass() {
    }
}
