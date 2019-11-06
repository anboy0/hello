package com.example.hello.aop.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.example.hello.aop.mybatis.DataRightInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.example.hello.mapper")
public class MybatisPlusConfig {

    @Autowired
    private DataSource dataSource;

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
        sqlSessionFactory.setPlugins(new Interceptor[]{
                new DataRightInterceptor()
        });
        return sqlSessionFactory.getObject();
    }

}
