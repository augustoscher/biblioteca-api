package br.com.objetive.biblioteca.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.objetive.biblioteca.tenant.TenantRepository;

@Configuration
public class FlywayConfig {

    public static String PUBLIC_SCHEMA = "public";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public Flyway flyway(DataSource dataSource) {
        logger.info("Migrating public schema ");
        Flyway flyway = new Flyway();
        flyway.setLocations("db/migration/default");
        flyway.setDataSource(dataSource);
        flyway.setSchemas(PUBLIC_SCHEMA);
        flyway.migrate();
        return flyway;
    }

    @Bean
    public Boolean tenantsFlyway(TenantRepository repository, DataSource dataSource) {
        repository.findAll().forEach(tenant -> {
            String schema = tenant.getSchemaName();
            Flyway flyway = new Flyway();
            flyway.setLocations("db/migration/tenants");
            flyway.setDataSource(dataSource);
            flyway.setSchemas(schema);
            flyway.migrate();
        });
        return true;
    }

}
