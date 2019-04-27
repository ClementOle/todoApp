package com.olewski.myapplication.model;

import java.util.Objects;

public class Task {
    private Integer id;
    private String text;
    private Boolean isDone;
    private Integer listId;

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

    public Task(Integer id, String text, Boolean isDone, Integer listId) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
        this.listId = listId;
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

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (getId() != null ? !getId().equals(task.getId()) : task.getId() != null) return false;
        if (getText() != null ? !getText().equals(task.getText()) : task.getText() != null)
            return false;
        if (isDone != null ? !isDone.equals(task.isDone) : task.isDone != null) return false;
        return getListId() != null ? getListId().equals(task.getListId()) : task.getListId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        result = 31 * result + (isDone != null ? isDone.hashCode() : 0);
        result = 31 * result + (getListId() != null ? getListId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isDone=" + isDone +
                ", listId=" + listId +
                '}';
    }

    public String toStringJson() {
        return "{" +
                "\"id\" : \"" + id + "\", " +
                "\"text\" : \"" + text + "\", " +
                "\"isDone\" : \'" + isDone + "\"" +
                "} ";
    }
}
