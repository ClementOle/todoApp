package com.olewski.myapplication.model;

import java.util.Objects;

public class Task {
    private Integer id;
    private String text;
    private Boolean isDone;


    public Task() {
    }

    public Task(String text, Boolean isDone) {
        this.text = text;
        this.isDone = isDone;
    }

    public Task(Integer id, String text, Boolean isDone) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (text != null ? !text.equals(task.text) : task.text != null) return false;
        return isDone != null ? isDone.equals(task.isDone) : task.isDone == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (isDone != null ? isDone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isDone=" + isDone +
                '}';
    }

    public String toStringJson() {
        return "{" +
                    "\'id\' : \'" + id + "\', " +
                    "\'text' : \'" + text + "\', " +
                    "\'isDone\' : \'" + isDone + "\'" +
                "} ";
    }
}
