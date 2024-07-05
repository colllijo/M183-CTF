package dev.coll.ctf.infrastructure.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class LiquibaseConfig {
  private final boolean dropFirst;
  private final DataSource dataSource;

  public LiquibaseConfig(
    @Value("${spring.liquibase.drop-first}") boolean dropFirst,
    DataSource dataSource) {
    this.dropFirst = dropFirst;
    this.dataSource = dataSource;
  }

  @Bean
  @DependsOn
  public SpringLiquibase liquibase() {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog("classpath:liquibase/main.xml");
    liquibase.setDataSource(dataSource);
    liquibase.setDropFirst(dropFirst);
    return liquibase;
  }

}
