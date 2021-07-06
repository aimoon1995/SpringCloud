package com.moon.accept_num.config.dataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @ClassName DataSourceGdDataConfig
 * @Description: TODO
 * @Author zyl
 * @Date 2020/11/18
 * @Version V1.0
 **/
@Configuration
@MapperScan(basePackages = "com.moon.moon_api.mapper.moon" , sqlSessionFactoryRef = "moonSqlSessionFactory")
public class DataSourceMoonDataConfig {


    @Bean(name = "moonSqlSessionFactory")
    public SqlSessionFactory moonSqlSessionFactory(@Qualifier("moonDataSource") DataSource dataSource)
            throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/moon/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "moonTransactionManager")
    public DataSourceTransactionManager moonTransactionManager(@Qualifier("moonDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "moonSqlSessionTemplate")
    public SqlSessionTemplate moonSqlSessionTemplate(@Qualifier("moonSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
