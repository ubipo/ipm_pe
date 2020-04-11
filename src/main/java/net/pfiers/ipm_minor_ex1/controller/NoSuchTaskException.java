package net.pfiers.ipm_minor_ex1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such task")
public class NoSuchTaskException extends RuntimeException {
    public final UUID uuid;

    public NoSuchTaskException(UUID uuid) {
        this.uuid = uuid;
    }
}
