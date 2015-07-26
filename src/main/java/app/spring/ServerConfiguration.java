package app.spring;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Server configuration class.
 * This is mainly used for side-effects of Spring annotations.
 * @author jonathan
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class ServerConfiguration extends WebMvcAutoConfiguration {
}