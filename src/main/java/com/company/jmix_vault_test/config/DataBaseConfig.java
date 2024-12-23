package com.company.jmix_vault_test.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.vault.core.VaultTemplate;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {
    private final VaultTemplate vaultTemplate;

    public DataBaseConfig(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }

    @Bean("dataSourceProperties")
    @Primary
    @ConfigurationProperties("main.datasource")
    DataSourceProperties dataSourcePropertiesVault(){
        DataSourceProperties properties = new DataSourceProperties();
        DbConfig dbConfig = new ObjectMapper().convertValue(
                vaultTemplate.read("kv/data/database").getData().get("data"),
                DbConfig.class
        );

        properties.setUrl(dbConfig.getUrl());
        properties.setUsername(dbConfig.getUsername());
        properties.setPassword(dbConfig.getPassword());

        return properties;
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource.hikari")
    DataSource dataSource(final DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}

