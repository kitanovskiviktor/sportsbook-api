package com.sportsbook.config.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean(name = "flywayMigration")
    public Object flywayMigration(
            @Qualifier("sharedDataSource") DataSource sharedDataSource,
            @Qualifier("brandADataSource") DataSource brandADataSource,
            @Qualifier("brandBDataSource") DataSource brandBDataSource) {

        Flyway.configure()
                .dataSource(sharedDataSource)
                .locations("classpath:db/migration/shared")
                .baselineOnMigrate(true)
                .load()
                .migrate();

        for (DataSource ds : new DataSource[]{ brandADataSource, brandBDataSource }) {
            Flyway.configure()
                    .dataSource(ds)
                    .locations("classpath:db/migration/tenant")
                    .baselineOnMigrate(true)
                    .load()
                    .migrate();
        }

        return new Object();
    }
}
