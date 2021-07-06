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
@MapperScan(basePackages = "com.moon.moon_api.mapper.moonSec" , sqlSessionFactoryRef = "moonSecSqlSessionFactory")
public class DataSourceMoonSecDataConfig {


    @Bean(name = "moonSecSqlSessionFactory")
    public SqlSessionFactory moonSecSqlSessionFactory(@Qualifier("moonSecDataSource") DataSource dataSource)
            throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/moonSec/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "moonSecTransactionManager")
    public DataSourceTransactionManager moonSecTransactionManager(@Qualifier("moonSecDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "moonSecSqlSessionTemplate")
    public SqlSessionTemplate moonSecSqlSessionTemplate(@Qualifier("moonSecSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
