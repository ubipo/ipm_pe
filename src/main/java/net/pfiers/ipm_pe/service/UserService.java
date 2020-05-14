package net.pfiers.ipm_pe.service;

import net.pfiers.ipm_pe.domain.User;
import net.pfiers.ipm_pe.dto.UserDto;
import net.pfiers.ipm_pe.dto.UserDtoPersisted;
import net.pfiers.ipm_pe.dto.UserDtoSignup;
import net.pfiers.ipm_pe.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final DtoMapper dtoMapper;


    @Autowired
    public UserService(UserRepo userRepo, DtoMapper dtoMapper) {
        this.userRepo = userRepo;
        this.dtoMapper = dtoMapper;
    }


    @Override
    public Optional<UserDtoPersisted> get(String username) {
        return userRepo.findByUsername(username).map(dtoMapper::toDto);
    }

    @Override
    public Optional<UserDtoPersisted> get(UUID uuid) {
        return userRepo.findByUuid(uuid).map(dtoMapper::toDto);
    }

    @Override
    public void setLocale(UUID uuid, Locale locale) {
        var user = getOrThrow(uuid);
        user.setLocale(locale);
        userRepo.save(user);
    }

    @Override
    public UserDtoPersisted add(UserDto dto) {
        User user;
        if (dto instanceof UserDtoPersisted) {
            user = getOrThrow(((UserDtoPersisted) dto).getUuid());
        } else {
            user = dtoMapper.toUser(dto);
        }
        return dtoMapper.toDto(userRepo.save(user));
    }

    @Override
    public UserDtoPersisted add(UserDtoSignup dto, Locale locale) {
        return dtoMapper.toDto(userRepo.save(dtoMapper.toUser(dto, locale)));
    }

    private User getOrThrow(UUID uuid) {
        return userRepo.findByUuid(uuid).orElseThrow(() -> new NoSuchElementException(
                String.format("User with uuid=%s", uuid)
        ));
    }
}
