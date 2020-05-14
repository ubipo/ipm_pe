package net.pfiers.ipm_pe.service;

import net.pfiers.ipm_pe.domain.UserPrincipal;
import net.pfiers.ipm_pe.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Service
public class LocaleResolverService implements LocaleResolver {
    private final IUserService userService;


    @Autowired
    public LocaleResolverService(IUserService userService) {
        this.userService = userService;
    }


    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        var principal = (UserPrincipal) request.getUserPrincipal();
        return userService.get(principal.getUuid()).map(UserDto::getLocale).orElse(Locale.ENGLISH);
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        var principal = (UserPrincipal) request.getUserPrincipal();
        userService.setLocale(principal.getUuid(), locale);
    }
}
