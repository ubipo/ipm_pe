package net.pfiers.ipm_minor_ex1.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false, nullable = false)
    @GeneratedValue()
    private UUID uuid;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Instant due;

    @ManyToOne
    private Task parent;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
    }

    public Task() { }

//    public Task(String title, String description, Instant due) {
//        this.uuid = UUID.randomUUID();
//        this.title = title;
//        this.description = description;
//        this.due = due;
//    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public Instant getDue() {
        return due;
    }

    public void setDue(Instant due) {
        this.due = due;
    }

    public Task getParent() {
        return parent;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }
}
