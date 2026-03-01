package dev.home.platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * MySQL datasource for tech improvement task list (per-type tables).
 * Active only when spring.datasource.mysql.url is set.
 */
@Configuration
@ConditionalOnProperty(name = "spring.datasource.mysql.url")
public class TechMysqlConfig {

    @Bean("techMysqlDataSource")
    public DataSource techMysqlDataSource(
            @Value("${spring.datasource.mysql.url}") String url,
            @Value("${spring.datasource.mysql.username:}") String username,
            @Value("${spring.datasource.mysql.password:}") String password) {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean("techMysqlJdbcTemplate")
    public JdbcTemplate techMysqlJdbcTemplate(
            @org.springframework.beans.factory.annotation.Qualifier("techMysqlDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
