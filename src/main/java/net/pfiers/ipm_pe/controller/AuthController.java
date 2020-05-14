package net.pfiers.ipm_pe.controller;

import net.pfiers.ipm_pe.dto.UserDto;
import net.pfiers.ipm_pe.dto.UserDtoSignup;
import net.pfiers.ipm_pe.service.ITaskService;
import net.pfiers.ipm_pe.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {
    private final IUserService userService;


    @Autowired
    public AuthController(IUserService userService) {
        this.userService = userService;
    }


    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping("/login")
    public ModelAndView getLogin(ModelMap m, @RequestParam Optional<Boolean> credError) {
        m.addAttribute("credError", credError.orElse(false));
        return new ModelAndView("login", m);
    }

    @GetMapping("/signup")
    public ModelAndView getSingup(ModelMap m) {
        m.addAttribute("user", new UserDtoSignup());
        return new ModelAndView("signup", m);
    }

    @PostMapping("/signup")
    public ModelAndView postSingup(ModelMap m, @ModelAttribute("user") @Valid UserDtoSignup dto, BindingResult bindingResult) {
        if (!dto.getPasswordRaw().equals(dto.getPasswordRawRepeated()))
            bindingResult.addError(new ObjectError("user", "Passwords do not match"));
        if (bindingResult.hasErrors()) {
            var mv = new ModelAndView("signup", m);
            mv.addObject("user", dto);
            mv.setStatus(HttpStatus.BAD_REQUEST);
            return mv;
        }
        userService.add(dto);
        return new ModelAndView(new RedirectView("/"));
    }
}
