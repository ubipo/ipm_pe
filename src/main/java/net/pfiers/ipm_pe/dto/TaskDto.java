package net.pfiers.ipm_pe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.pfiers.ipm_pe.domain.Task;
import net.pfiers.ipm_pe.util.SlugUUIDFormatter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class TaskDto {
    public static final ZoneId ZONE_ID = ZoneId.of("CET");

    @JsonFormat()
    private UUID uuid;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Instant due;
    private TaskDto parent;


    // Base getters/setters

    public UUID getUuid() {
        return uuid;
    }

    public String getSlug() {
        return SlugUUIDFormatter.print(getUuid());
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

    public TaskDto getParent() {
        return parent;
    }

    public void setParent(TaskDto parent) {
        this.parent = parent;
    }


    // Derived getters/setters

    public Optional<LocalDateTime> getDueLocal() {
        var due = getDue();
        if (due == null)
            return Optional.empty();

        return Optional.of(due.atZone(ZONE_ID).toLocalDateTime());
    }

    public void setDueLocal(LocalDateTime dateTime) {
        setDue(dateTime.atZone(ZONE_ID).toInstant());
    }


    public Optional<LocalDate> getDueDateOpt() {
        return getDueLocal().map(LocalDateTime::toLocalDate);
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate getDueDate() {
        return getDueDateOpt().orElse(null);
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public void setDueDate(LocalDate date) {
        if (date != null)
            setDueLocal(LocalDateTime.of(date, getDueTimeOpt().orElse(LocalTime.NOON)));
    }


    public Optional<LocalTime> getDueTimeOpt() {
        return getDueLocal().map(LocalDateTime::toLocalTime);
    }

    @DateTimeFormat(pattern = "kk:mm")
    public LocalTime getDueTime() {
        return getDueTimeOpt().orElse(null);
    }

    @DateTimeFormat(pattern = "kk:mm")
    public void setDueTime(LocalTime time) {
        if (time != null)
            setDueLocal(LocalDateTime.of(getDueDateOpt().orElse(LocalDate.now()), time));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(getUuid(), taskDto.getUuid()) &&
                Objects.equals(getTitle(), taskDto.getTitle()) &&
                Objects.equals(getDescription(), taskDto.getDescription()) &&
                Objects.equals(getDue(), taskDto.getDue()) &&
                Objects.equals(getParent(), taskDto.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getTitle(), getDescription(), getDue(), getParent());
    }

    // Mock objects

    public static TaskDto mockObj() {
        var dto = new TaskDto();
        dto.setDue(Instant.now().plus(Duration.of(5, ChronoUnit.HOURS)));
        dto.setTitle("Do groceries");
        dto.setDescription("Task description:\na. Get a\nb. Get b");
        return dto;
    }
}
