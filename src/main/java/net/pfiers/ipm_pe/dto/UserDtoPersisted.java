package net.pfiers.ipm_pe.dto;

import java.util.UUID;

public class UserDtoPersisted extends UserDto {
    UUID uuid;


    public UserDtoPersisted(String username, String password, boolean isAdmin, UUID uuid) {
        super(username, password, isAdmin);
        setUuid(uuid);
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
