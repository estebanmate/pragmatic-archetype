#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseAwareExtension implements BeforeAllCallback, AfterAllCallback, ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final PostgreSQLContainer container;

    public DatabaseAwareExtension() {
        this.container = new PostgreSQLContainer("postgres:12.1")
                .withDatabaseName("integration-test")
                .withUsername("sa")
                .withPassword("sa");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        container.stop();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        container.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        container.start();
        TestPropertyValues.of(
                "spring.datasource.url=" + container.getJdbcUrl(),
                "spring.datasource.username=" + container.getUsername(),
                "spring.datasource.password=" + container.getPassword()
        ).applyTo(applicationContext.getEnvironment());
    }
}
