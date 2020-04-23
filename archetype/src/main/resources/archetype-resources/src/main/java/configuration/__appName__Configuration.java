#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration;

import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ${appName}Configuration {


    @Bean
    public Settings jooqSettings() {
        return new Settings()
                .withRenderNameCase(RenderNameCase.AS_IS)
                .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactory(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
