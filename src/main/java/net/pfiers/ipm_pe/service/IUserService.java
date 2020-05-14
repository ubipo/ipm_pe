package net.pfiers.ipm_pe.service;

import net.pfiers.ipm_pe.dto.UserDto;
import net.pfiers.ipm_pe.dto.UserDtoPersisted;
import net.pfiers.ipm_pe.dto.UserDtoSignup;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    Optional<UserDtoPersisted> get(String username);

    Optional<UserDtoPersisted> get(UUID uuid);

    UserDtoPersisted add(UserDto dto);

    UserDtoPersisted add(UserDtoSignup dto);
}
