package net.pfiers.ipm_pe;

import net.pfiers.ipm_pe.util.SlugUUIDFormatter;
import net.pfiers.ipm_pe.util.StringArrToInstantConverter;
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
