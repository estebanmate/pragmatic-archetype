#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseAwareExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseAwareExtension.class);

    private static DSLContext dslContext;
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
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        logger.info("truncate all tables");
        Assertions.fail("uncomment next lines after jooq schema generation and remove this line");
        /*
        DefaultSchema.DEFAULT_SCHEMA
                .getTables()
                .forEach(table -> dslContext.truncate(table.getName().toLowerCase()).cascade().execute());

         */
    }


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        container.start();
        dslContext = DSL.using(container.getJdbcUrl(), container.getUsername(), container.getPassword());
        TestPropertyValues.of(
                "spring.datasource.url=" + container.getJdbcUrl(),
                "spring.datasource.username=" + container.getUsername(),
                "spring.datasource.password=" + container.getPassword()
        ).applyTo(applicationContext.getEnvironment());
    }
}
