package net.pfiers.ipm_pe;

import net.pfiers.ipm_pe.dto.UserDto;
import net.pfiers.ipm_pe.service.IUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class IpmPeApplication {
	private final IUserService userService;


	public IpmPeApplication(IUserService userService) {
		this.userService = userService;
	}


	@PostConstruct
	public void init() {
		var pass = new BCryptPasswordEncoder().encode("toor");
		userService.add(new UserDto("admin", pass, true));
		userService.add(new UserDto("user", pass, false));
	}

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(IpmPeApplication.class, args);
	}
}
