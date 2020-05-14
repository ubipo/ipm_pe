package net.pfiers.ipm_pe.service;

import net.pfiers.ipm_pe.domain.User;
import net.pfiers.ipm_pe.dto.UserDto;
import net.pfiers.ipm_pe.dto.UserDtoPersisted;
import net.pfiers.ipm_pe.dto.UserDtoSignup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class DtoMapper {
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public DtoMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public UserDtoPersisted toDto(User user) {
        return new UserDtoPersisted(
                user.getUsername(), user.getPassword(), user.isAdmin(), user.getUuid(), new Locale(user.getLocale())
        );
    }

    public User toUser(UserDto dto) {
        return new User(dto.getUsername(), dto.getPassword(), dto.isAdmin(), dto.getLocale());
    }

    public User toUser(UserDtoSignup dto, Locale locale) {
        return new User(dto.getUsername(), passwordEncoder.encode(dto.getPasswordRaw()), dto.isAdmin(), locale);
    }
}
