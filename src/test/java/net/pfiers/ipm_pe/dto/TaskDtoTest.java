package net.pfiers.ipm_pe.dto;

import net.pfiers.ipm_pe.util.StringArrToInstantConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskDtoTest {
    private TaskDto task;
    private static final LocalDate tomDate = LocalDate.of(2020, 3, 10);
    private static final LocalTime tomTime = LocalTime.of(16, 34);
    private static final ZonedDateTime tenthOfMarch = ZonedDateTime.of(tomDate, tomTime, TaskDto.ZONE_ID);

    @BeforeEach
    public void beforeEach() {
        task = new TaskDto();
        task.setDue(tenthOfMarch.toInstant());
    }


    @Test
    void getDueLocal() {
        assertEquals(tenthOfMarch.toLocalDateTime(), task.getDueLocal().orElse(null));
    }

    @Test
    void getDueLocalEmpty() {
        task.setDue(null);
        assertTrue(task.getDueLocal().isEmpty());
    }

    @Test
    void setGetDueLocal() {
        task.setDueLocal(tenthOfMarch.toLocalDateTime());
        assertEquals(tenthOfMarch.toLocalDateTime(), task.getDueLocal().orElse(null));
    }

    @Test
    void getDueDate() {
        assertEquals(tenthOfMarch.toLocalDate(), task.getDueDate());
    }

    @Test
    void getDueDateNull() {
        task.setDue(null);
        assertNull(task.getDueDate());
    }

    @Test
    void setGetDueDate() {
        var date = LocalDate.of(2030, 4, 11);
        task.setDueDate(date);
        assertEquals(date, task.getDueDate());
    }

    @Test
    void getDueTime() {
        assertEquals(tenthOfMarch.toLocalTime(), task.getDueTime());
    }

    @Test
    void getDueTimeNull() {
        task.setDue(null);
        assertNull(task.getDueDate());
    }

    @Test
    void setGetDueTime() {
        var time = LocalTime.of(2, 12);
        task.setDueTime(time);
        assertEquals(time, task.getDueTime());
    }

    @Test
    void setGetDueTimeIndependentDate() {
        var newDate = LocalDate.of(2030, 4, 11);
        task.setDueDate(newDate);
        assertEquals(tomTime, task.getDueTime());
    }

    @Test
    void setGetDueDateIndependentTime() {
        var newTime = LocalTime.of(2, 12);
        task.setDueTime(newTime);
        assertEquals(tomDate, task.getDueDate());
    }
}