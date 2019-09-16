package org.x.pragmatic.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Configuration
public class PragmaticConfiguration {
}
