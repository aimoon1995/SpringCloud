
package com.moon.moon_api.config.dataSource;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    /**
     * moon 库
     */
    @Bean(name = "moonDataSource")
    @Qualifier("moonDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource moonDataSource() {
        return  new DruidDataSource();
    }


    /**
     * moonSec库
     */
    @Bean(name = "moonSecDataSource")
    @Qualifier("moonSecDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource moonSecDataSource() {
        return  new DruidDataSource();
    }




}

