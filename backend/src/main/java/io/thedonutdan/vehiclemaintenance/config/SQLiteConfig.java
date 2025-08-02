package io.thedonutdan.vehiclemaintenance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.JDBC;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class SQLiteConfig {
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBC.class); // Important
        ds.setUrl("jdbc:sqlite:vehicle-maintenance.db");
        return ds;
    }
}
