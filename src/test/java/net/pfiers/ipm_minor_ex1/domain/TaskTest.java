package net.pfiers.ipm_minor_ex1.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task = new Task();

    @BeforeEach
    void beforeEach() {
        task = new Task();
    }

    @Test
    void onCreate() {
        task.onCreate();
        assertNotNull(task.getUuid());
    }

    @Test
    void setGetUuid() {
        var uuid = UUID.randomUUID();
        task.setUuid(uuid);
        assertEquals(uuid, task.getUuid());
    }

    @Test
    void setGetTitle() {
        var title = "title";
        task.setTitle(title);
        assertEquals(title, task.getTitle());
    }

    @Test
    void setGetDescription() {
        var description = "description";
        task.setDescription(description);
        assertEquals(description, task.getDescription());
    }

    @Test
    void setGetDue() {
        var due = Instant.now().plus(3, ChronoUnit.DAYS);
        task.setDue(due);
        assertEquals(due, task.getDue());
    }

    @Test
    void setGetParent() {
        var parent = new Task();
        task.setParent(parent);
        assertEquals(parent, task.getParent());
    }
}