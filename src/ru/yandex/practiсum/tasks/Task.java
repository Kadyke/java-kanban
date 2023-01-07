package ru.yandex.practiсum.tasks;

public class Task {
    protected String title;
    protected String description;
    protected final Integer id;
    protected Statuses status;

    public Task(Integer id) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public String toString() {
        return title + "\n" + description + "\n" + id + "\n" + status;
    }
}
