package net.pfiers.ipm_pe.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;
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

    @Column(length=5)
    private String locale;


    public User(String username, String password, boolean isAdmin, Locale locale) {
        setUsername(username);
        setPassword(password);
        setAdmin(isAdmin);
        setLocale(locale);
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Locale getLocaleObj() {
        return new Locale(getLocale());
    }

    public void setLocale(Locale locale) {
        setLocale(locale.toString());
    }
}
