package dk.nine.demo.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

public class EnvironmentVariableLogger implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentVariableLogger.class);


    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment env = event.getEnvironment();

        // Log specific environment variables or properties
        logger.info("SPRING_DATASOURCE_PASSWORD: {}", env.getProperty("SPRING_DATASOURCE_PASSWORD"));

        // Log all environment variables (Optional)
        logger.info("All Environment Variables: {}", System.getenv());
    }
}
