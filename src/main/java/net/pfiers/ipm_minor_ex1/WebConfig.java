package net.pfiers.ipm_minor_ex1;

import net.pfiers.ipm_minor_ex1.util.SlugUUIDFormatter;
import net.pfiers.ipm_minor_ex1.util.StringArrToInstantConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringArrToInstantConverter());
        registry.addFormatter(new SlugUUIDFormatter());
    }
}
