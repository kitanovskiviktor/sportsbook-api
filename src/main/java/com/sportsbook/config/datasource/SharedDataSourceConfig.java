package com.sportsbook.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.sportsbook.repository.shared",
        entityManagerFactoryRef = "sharedEntityManagerFactory",
        transactionManagerRef = "sharedTransactionManager"
)
public class SharedDataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.shared")
    public DataSource sharedDataSource() {
        return new HikariDataSource();
    }

    @Primary
    @Bean
    @DependsOn("flywayMigration")
    public LocalContainerEntityManagerFactoryBean sharedEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("sharedDataSource") DataSource dataSource) {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "validate");
        props.put("hibernate.show_sql", "true");
        return builder
                .dataSource(dataSource)
                .packages("com.sportsbook.model.shared")
                .persistenceUnit("shared")
                .properties(props)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager sharedTransactionManager(
            @Qualifier("sharedEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}