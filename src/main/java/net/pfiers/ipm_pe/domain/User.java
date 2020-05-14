package net.pfiers.ipm_pe.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp created;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private boolean isAdmin;


    public User(String username, String password, boolean isAdmin) {
        setUsername(username);
        setPassword(password);
        setAdmin(isAdmin);
    }

    public User() { }

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
    }



    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
