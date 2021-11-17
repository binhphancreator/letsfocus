package com.app.letsfocus.ui.add_todo;

public class TodoModel {
    int todoId;
    String todoName;
    String todoTime;
    String todoDuration;
    String todoDetail;

    public TodoModel(String todoName, String todoTime, String todoDuration, String todoDetail) {
        this.todoName = todoName;
        this.todoTime = todoTime;
        this.todoDuration = todoDuration;
        this.todoDetail = todoDetail;
    }

    public TodoModel(int todoId, String todoName, String todoTime, String todoDuration, String todoDetail) {
        this.todoId = todoId;
        this.todoName = todoName;
        this.todoTime = todoTime;
        this.todoDuration = todoDuration;
        this.todoDetail = todoDetail;
    }


    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public String getTodoTime() {
        return todoTime;
    }

    public void setTodoTime(String todoTime) {
        this.todoTime = todoTime;
    }

    public String getTodoDuration() {
        return todoDuration;
    }

    public void setTodoDuration(String todoDuration) {
        this.todoDuration = todoDuration;
    }

    public String getTodoDetail() {
        return todoDetail;
    }

    public void setTodoDetail(String todoDetail) {
        this.todoDetail = todoDetail;
    }

}
