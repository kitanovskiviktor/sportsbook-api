package com.sportsbook.config.datasource;

import com.sportsbook.config.tenant.TenantRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.sportsbook.repository.tenant",
        entityManagerFactoryRef = "tenantEntityManagerFactory",
        transactionManagerRef = "tenantTransactionManager"
)
public class TenantDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.brand-a")
    public DataSource brandADataSource() {
        return new HikariDataSource();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.brand-b")
    public DataSource brandBDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public DataSource tenantRoutingDataSource(
            @Qualifier("brandADataSource") DataSource brandA,
            @Qualifier("brandBDataSource") DataSource brandB) {
        TenantRoutingDataSource routing = new TenantRoutingDataSource();

        Map<Object, Object> targets = new HashMap<>();
        targets.put("brand_a", brandA);
        targets.put("brand_b", brandB);

        routing.setTargetDataSources(targets);
        routing.setDefaultTargetDataSource(brandA);
        return routing;
    }

    @Bean
    @DependsOn("flywayMigration")
    public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("tenantRoutingDataSource") DataSource dataSource) {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "validate");
        props.put("hibernate.show_sql", "true");
        return builder
                .dataSource(dataSource)
                .packages("com.sportsbook.model.tenant")
                .persistenceUnit("tenant")
                .properties(props)
                .build();
    }

    @Bean
    public PlatformTransactionManager tenantTransactionManager(
            @Qualifier("tenantEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
